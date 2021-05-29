package com.github.messenger.config;

import com.github.messenger.handlers.ExceptionHandler;
import com.github.messenger.handlers.HttpHandler;

public class HttpHandlerConfig {

    private static ExceptionHandler exceptionHandler = new ExceptionHandler();

    private static HttpHandler httpHandler = new HttpHandler(
            ControllerConfig.getAuthorizationController(),
            ControllerConfig.getRegistrationController(),
            exceptionHandler
    );

    public static HttpHandler getHttpHandler() {
        return httpHandler;
    }

}
