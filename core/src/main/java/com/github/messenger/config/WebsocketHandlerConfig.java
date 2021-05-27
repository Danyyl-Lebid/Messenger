package com.github.messenger.config;

import com.github.messenger.handlers.WebsocketHandler;
import com.github.messenger.network.Broker;
import com.github.messenger.network.RoomConnectionPools;
import com.github.messenger.network.WebsocketConnectionPool;

import java.util.HashMap;
import java.util.Map;

public class WebsocketHandlerConfig {

    private static final WebsocketConnectionPool websocketConnectionPool = new WebsocketConnectionPool();

    private static final RoomConnectionPools roomConnectionPools = new RoomConnectionPools(
            ServiceConfig.getChatService(), ServiceConfig.getUserChatRelationService()
    );

    private static final Broker broker = new Broker();

    private static final WebsocketHandler websocketHandler = new WebsocketHandler(
            getWebsocketConnectionPool(),
            getRoomConnectionPools(),
            getBroker(),
            ControllerConfig.getGlobalMessageController(),
            ControllerConfig.getMessageController()
    );

    public static WebsocketHandler getWebsocketHandler() {
        return websocketHandler;
    }

    public static WebsocketConnectionPool getWebsocketConnectionPool() {
        return websocketConnectionPool;
    }

    public static RoomConnectionPools getRoomConnectionPools() {
        return roomConnectionPools;
    }

    public static Broker getBroker() {
        return broker;
    }
}
