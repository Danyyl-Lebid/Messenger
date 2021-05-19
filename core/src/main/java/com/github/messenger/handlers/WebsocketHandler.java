package com.github.messenger.handlers;

import com.github.messenger.network.Broker;
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

    private final WebsocketConnectionPool websocketConnectionPool;

    private final Broker broker;

    public WebsocketHandler(WebsocketConnectionPool websocketConnectionPool, Broker broker) {
        this.websocketConnectionPool = websocketConnectionPool;
        this.broker = broker;
    }

    @OnMessage
    public void messages(Session session, String payload) {
        try {
            System.out.println(payload);
            Envelope envelope = JsonHelper.fromJson(payload, Envelope.class).orElseThrow();
            PrivateToken result = PrivateTokenProvider.decode(envelope.getPayload());
            PrivateTokenProvider.validateToken(result);
            switch (envelope.getTopic()) {
                case LOGIN:
                    websocketConnectionPool.addSession(result.getLogin(), session);
                    broker.broadcast(websocketConnectionPool.getSessions(), envelope);
                    break;
                case MESSAGE:
                    broker.broadcast(websocketConnectionPool.getSessions(), envelope);
                    broker.send(session,envelope);
                    break;
                case LOGOUT:
                    broker.broadcast(websocketConnectionPool.getSessions(), envelope);
                    websocketConnectionPool.removeSession(result.getLogin());
                    websocketConnectionPool.getSession(result.getLogin()).close();
                    break;
            }
        } catch (Throwable e) {
            //TODO: single send to user about error
            log.warn("Enter {}", e.getMessage());
        }
    }
}
