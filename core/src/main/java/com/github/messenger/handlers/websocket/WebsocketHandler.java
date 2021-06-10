package com.github.messenger.handlers.websocket;

import com.github.messenger.exceptions.ExpiredToken;
import com.github.messenger.network.Broker;
import com.github.messenger.network.RoomConnectionPools;
import com.github.messenger.network.WebsocketConnectionPool;
import com.github.messenger.payload.Envelope;
import com.github.messenger.payload.Token;
import com.github.messenger.utils.JsonHelper;
import com.github.messenger.utils.TokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.OnMessage;
import javax.websocket.Session;

public class WebsocketHandler {

    private static final Logger log = LoggerFactory.getLogger(WebsocketHandler.class);

    private final WebsocketConnectionPool globalConnectionPool;

    private final RoomConnectionPools roomConnectionPools;

    private final Broker broker;

    private final WebsocketHandlerStrategyMap strategyMap;

    public WebsocketHandler(
            WebsocketConnectionPool globalConnectionPool,
            RoomConnectionPools roomConnectionPools,
            Broker broker,
            WebsocketHandlerStrategyMap strategyMap
    ) {
        this.globalConnectionPool = globalConnectionPool;
        this.roomConnectionPools = roomConnectionPools;
        this.broker = broker;
        this.strategyMap = strategyMap;
    }


    @OnMessage
    public void messages(Session session, String payload) {
        log.info("Payload - {}", payload);
        try {
            Envelope envelope = JsonHelper.fromJson(payload, Envelope.class).orElseThrow();
            Token token = TokenProvider.decode(envelope.getToken());
            if (!TokenProvider.validateToken(token)) {
                throw new ExpiredToken();
            }
            envelope.setToken("empty-token");
            strategyMap.getMap().get(envelope.getTopic()).doHandle(session, envelope, token);
        } catch (Throwable e) {
            broker.sendAsync(session, String.format("Error %s has occurred on server", e.getClass().getName()));
            log.warn(String.format("Exception %s - Message: %s", e.getClass().getName(), e.getMessage()));
        }
    }
}
