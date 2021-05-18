package com.github.messenger.config;

import com.github.messenger.controllers.authorization.IAuthorizationController;
import com.github.messenger.controllers.authorization.impl.AuthorizationController;
import com.github.messenger.controllers.registration.IRegistrationController;
import com.github.messenger.controllers.registration.impl.RegistrationController;

public class ControllerConfig {

    private static IAuthorizationController authorizationController = new AuthorizationController(ServiceConfig.getUserService());

    private static IRegistrationController registrationController = new RegistrationController(ServiceConfig.getUserService());

    public static IAuthorizationController getAuthorizationController() {
        return authorizationController;
    }

    public static IRegistrationController getRegistrationController() {
        return registrationController;
    }
}
