package com.github.messenger.config;

import com.github.messenger.controllers.authorization.IAuthorizationController;
import com.github.messenger.controllers.authorization.impl.AuthorizationController;
import com.github.messenger.controllers.chat.IChatController;
import com.github.messenger.controllers.chat.impl.ChatController;
import com.github.messenger.controllers.global.message.IGlobalMessageController;
import com.github.messenger.controllers.global.message.impl.GlobalMessageController;
import com.github.messenger.controllers.message.IMessageController;
import com.github.messenger.controllers.message.impl.MessageController;
import com.github.messenger.controllers.registration.IRegistrationController;
import com.github.messenger.controllers.registration.impl.RegistrationController;
import com.github.messenger.controllers.status.IStatusController;
import com.github.messenger.controllers.status.impl.StatusController;

import java.util.Objects;

public class ControllerConfig {

    private static final IAuthorizationController authorizationController = new AuthorizationController(ServiceConfig.getUserService());

    private static final IRegistrationController registrationController = new RegistrationController(ServiceConfig.getUserService());

    private static IGlobalMessageController globalMessageController;

    private static IChatController chatController;

    private static final IMessageController messageController = new MessageController(
            ServiceConfig.getMessageService(),
            WebsocketHandlerConfig.getRoomConnectionPools(),
            WebsocketHandlerConfig.getBroker()
    );

    private static IStatusController statusController;

    public static IAuthorizationController getAuthorizationController() {
        return authorizationController;
    }

    public static IRegistrationController getRegistrationController() {
        return registrationController;
    }

    public static IMessageController getMessageController() {
        return messageController;
    }

    public static IGlobalMessageController getGlobalMessageController() {
        if(Objects.isNull(globalMessageController)){
            globalMessageController = new GlobalMessageController(
                    ServiceConfig.getGlobalMessageService(),
                    WebsocketHandlerConfig.getWebsocketConnectionPool(),
                    WebsocketHandlerConfig.getBroker()
            );
        }
        return globalMessageController;
    }

    public static IChatController getChatController() {
        if(Objects.isNull(chatController)){
            chatController = new ChatController(
                    ServiceConfig.getUserService(),
                    ServiceConfig.getChatService(),
                    ServiceConfig.getUserChatRelationService()
            );
        }
        return chatController;
    }

    public static IStatusController getStatusController() {
        if(Objects.isNull(statusController)){
            statusController = new StatusController(
                    ServiceConfig.getUserService()
            );
        }
        return statusController;
    }
}
