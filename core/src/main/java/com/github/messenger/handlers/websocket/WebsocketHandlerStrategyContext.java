package com.github.messenger.handlers.websocket;

import com.github.messenger.controllers.chat.IChatController;
import com.github.messenger.controllers.global.message.IGlobalMessageController;
import com.github.messenger.controllers.message.IMessageController;
import com.github.messenger.controllers.status.IStatusController;
import com.github.messenger.network.Broker;
import com.github.messenger.network.RoomConnectionPools;
import com.github.messenger.network.WebsocketConnectionPool;

public class WebsocketHandlerStrategyContext {

    private final WebsocketConnectionPool globalConnectionPool;

    private final RoomConnectionPools roomConnectionPools;

    private final IGlobalMessageController globalMessageController;

    private final IMessageController messageController;

    private final IStatusController statusController;

    private final IChatController chatController;

    private final Broker broker;

    public WebsocketHandlerStrategyContext(
            WebsocketConnectionPool globalConnectionPool,
            RoomConnectionPools roomConnectionPools,
            IGlobalMessageController globalMessageController,
            IMessageController messageController,
            IStatusController statusController,
            IChatController chatController,
            Broker broker
    ) {
        this.globalConnectionPool = globalConnectionPool;
        this.roomConnectionPools = roomConnectionPools;
        this.globalMessageController = globalMessageController;
        this.messageController = messageController;
        this.statusController = statusController;
        this.chatController = chatController;
        this.broker = broker;
    }

    public WebsocketConnectionPool getGlobalConnectionPool() {
        return globalConnectionPool;
    }

    public RoomConnectionPools getRoomConnectionPools() {
        return roomConnectionPools;
    }

    public IGlobalMessageController getGlobalMessageController() {
        return globalMessageController;
    }

    public IMessageController getMessageController() {
        return messageController;
    }

    public IStatusController getStatusController() {
        return statusController;
    }

    public IChatController getChatController() {
        return chatController;
    }

    public Broker getBroker() {
        return broker;
    }
}
