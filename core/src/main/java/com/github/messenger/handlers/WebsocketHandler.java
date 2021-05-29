package com.github.messenger.handlers;

import com.github.messenger.controllers.global.message.IGlobalMessageController;
import com.github.messenger.controllers.message.IMessageController;
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
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.util.Objects;

public class WebsocketHandler {

    private static final Logger log = LoggerFactory.getLogger(WebsocketHandler.class);

    private final WebsocketConnectionPool globalConnectionPool;

    private final RoomConnectionPools roomConnectionPools;

    private final IGlobalMessageController globalMessageController;

    private final IMessageController messageController;

    private final Broker broker;

    public WebsocketHandler(
            WebsocketConnectionPool globalConnectionPool,
            RoomConnectionPools roomConnectionPools,
            Broker broker,
            IGlobalMessageController globalMessageController,
            IMessageController messageController
    ) {
        this.globalConnectionPool = globalConnectionPool;
        this.roomConnectionPools = roomConnectionPools;
        this.broker = broker;
        this.globalMessageController = globalMessageController;
        this.messageController = messageController;
    }

    @OnMessage
    public void messages(Session session, String payload) {
        try {
            Envelope envelope = JsonHelper.fromJson(payload, Envelope.class).orElseThrow();
            PrivateToken result = PrivateTokenProvider.decode(envelope.getToken());
            if(!PrivateTokenProvider.validateToken(result)){
                throw new ExpiredToken();
            }
            switch (envelope.getTopic()) {
                case LOGIN:
                    globalConnectionPool.addSession(result.getUserId(), session);
                    roomConnectionPools.addSession(result.getUserId(), session);
                    globalMessageController.sendHistory(session);
                    broker.broadcast(globalConnectionPool.getSessions(), envelope.getPayload());
                    break;
                case GLOBAL_MESSAGE:
                    if(Objects.isNull(this.globalMessageController)){
                        System.out.println("Is null in websocket handler");
                    }
                    this.globalMessageController.save(result.getUserId(), envelope.getPayload());
                    this.globalMessageController.broadcast(envelope.getPayload());
                    break;
                case MESSAGE:
                    messageController.save(result.getUserId(), envelope.getPayload());
                    messageController.broadcast(envelope.getPayload());
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
