package com.github.messenger.config;

import com.github.messenger.handlers.ExceptionHandler;
import com.github.messenger.handlers.HttpHandler;

public class HttpHandlerConfig {

    private static final ExceptionHandler exceptionHandler = new ExceptionHandler();

    private static final HttpHandler httpHandler = new HttpHandler(
            ControllerConfig.getAuthorizationController(),
            ControllerConfig.getRegistrationController(),
            ControllerConfig.getChatController(),
            exceptionHandler
    );

    public static HttpHandler getHttpHandler() {
        return httpHandler;
    }

}
