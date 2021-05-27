package com.github.messenger.config;

import com.github.messenger.controllers.authorization.IAuthorizationController;
import com.github.messenger.controllers.authorization.impl.AuthorizationController;
import com.github.messenger.controllers.global.message.IGlobalMessageController;
import com.github.messenger.controllers.global.message.impl.GlobalMessageController;
import com.github.messenger.controllers.message.IMessageController;
import com.github.messenger.controllers.message.impl.MessageController;
import com.github.messenger.controllers.registration.IRegistrationController;
import com.github.messenger.controllers.registration.impl.RegistrationController;

public class ControllerConfig {

    private static IAuthorizationController authorizationController = new AuthorizationController(ServiceConfig.getUserService());

    private static IRegistrationController registrationController = new RegistrationController(ServiceConfig.getUserService());

    private static IGlobalMessageController globalMessageController = new GlobalMessageController(
            ServiceConfig.getGlobalMessageService(),
            WebsocketHandlerConfig.getWebsocketConnectionPool(),
            WebsocketHandlerConfig.getBroker()
    );

    private static IMessageController messageController = new MessageController(
            ServiceConfig.getMessageService(),
            WebsocketHandlerConfig.getRoomConnectionPools(),
            WebsocketHandlerConfig.getBroker()
    );

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
        return globalMessageController;
    }

}
