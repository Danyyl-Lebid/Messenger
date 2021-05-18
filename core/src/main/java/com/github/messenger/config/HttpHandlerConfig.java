package com.github.messenger.config;

import com.github.messenger.handlers.HttpHandler;

public class HttpHandlerConfig {

    private static HttpHandler httpHandler = new HttpHandler(ControllerConfig.getAuthorizationController(), ControllerConfig.getRegistrationController());

    public static HttpHandler getHttpHandler() {
        return httpHandler;
    }

}
