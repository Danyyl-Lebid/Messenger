package com.github.messenger.config;

import com.github.messenger.controllers.chat.IChatController;
import com.github.messenger.controllers.global.message.IGlobalMessageController;
import com.github.messenger.controllers.message.IMessageController;
import com.github.messenger.controllers.status.IStatusController;
import com.github.messenger.handlers.websocket.WebsocketHandler;
import com.github.messenger.handlers.websocket.WebsocketHandlerStrategyContext;
import com.github.messenger.handlers.websocket.WebsocketHandlerStrategyMap;
import com.github.messenger.network.Broker;
import com.github.messenger.network.RoomConnectionPools;
import com.github.messenger.network.WebsocketConnectionPool;

public class WebsocketHandlerConfig {

    private static final WebsocketConnectionPool websocketConnectionPool = new WebsocketConnectionPool();

    private static final RoomConnectionPools roomConnectionPools = new RoomConnectionPools(
            ServiceConfig.getChatService(), ServiceConfig.getUserChatRelationService()
    );

    private static final Broker broker = new Broker();

    private static final WebsocketHandlerStrategyContext websocketContext = new WebsocketHandlerStrategyContext(
            getWebsocketConnectionPool(),
            getRoomConnectionPools(),
            ControllerConfig.getGlobalMessageController(),
            ControllerConfig.getMessageController(),
            ControllerConfig.getStatusController(),
            ControllerConfig.getChatController(),
            getBroker()
    );

    private static final WebsocketHandlerStrategyMap websocketStrategyMap = new WebsocketHandlerStrategyMap(
            websocketContext
    );

    private static final WebsocketHandler websocketHandler = new WebsocketHandler(
            getWebsocketConnectionPool(),
            getRoomConnectionPools(),
            getBroker(),
            websocketStrategyMap
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
