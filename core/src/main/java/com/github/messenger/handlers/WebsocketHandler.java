package com.github.messenger.handlers;

import com.github.messenger.controllers.global.message.IGlobalMessageController;
import com.github.messenger.exceptions.ExpiredToken;
import com.github.messenger.network.Broker;
import com.github.messenger.network.RoomConnectionPools;
import com.github.messenger.network.WebsocketConnectionPool;
import com.github.messenger.payload.Envelope;
import com.github.messenger.payload.PrivateToken;
import com.github.messenger.utils.JsonHelper;
import com.github.messenger.utils.PrivateTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.OnMessage;
import javax.websocket.Session;

public class WebsocketHandler {

    private static final Logger log = LoggerFactory.getLogger(WebsocketHandler.class);

    private final WebsocketConnectionPool globalConnectionPool;

    private final RoomConnectionPools roomConnectionPools;

    private final IGlobalMessageController globalMessageController;

    private final Broker broker;

    public WebsocketHandler(
            WebsocketConnectionPool globalConnectionPool,
            RoomConnectionPools roomConnectionPools,
            Broker broker,
            IGlobalMessageController globalMessageController
    ) {
        this.globalConnectionPool = globalConnectionPool;
        this.roomConnectionPools = roomConnectionPools;
        this.broker = broker;
        this.globalMessageController = globalMessageController;
    }

    @OnMessage
    public void messages(Session session, String input) {
        try {
            Envelope envelope = JsonHelper.fromJson(input, Envelope.class).orElseThrow();
            PrivateToken result = PrivateTokenProvider.decode(envelope.getToken());
            if(!PrivateTokenProvider.validateToken(result)){
                throw new ExpiredToken();
            }
            switch (envelope.getTopic()) {
                case LOGIN:
                    globalConnectionPool.addSession(result.getUserId(), session);
                    roomConnectionPools.addSession(result.getUserId(), session);
                    broker.broadcast(globalConnectionPool.getSessions(), envelope.getPayload());
                    break;
                case GLOBAL_MESSAGE:
                    globalMessageController.save(result.getUserId(), envelope.getPayload());
                    globalMessageController.broadcast(envelope.getPayload());
                    break;
                case LOGOUT:
                    broker.broadcast(globalConnectionPool.getSessions(), envelope.getPayload());
                    globalConnectionPool.removeSession(result.getUserId());
                    globalConnectionPool.getSession(result.getUserId()).close();
                    break;
            }
        } catch (Throwable e) {
            //TODO: single send to user about error
            log.warn("Enter {}", e.getMessage());
        }
    }
}
