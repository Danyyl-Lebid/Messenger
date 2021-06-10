package com.github.messenger.config;

import com.github.messenger.handlers.ExceptionHandler;
import com.github.messenger.handlers.http.HttpHandler;
import com.github.messenger.handlers.http.HttpHandlerStrategyContext;
import com.github.messenger.handlers.http.HttpHandlerStrategyMap;

public class HttpHandlerConfig {

    private static final ExceptionHandler exceptionHandler = new ExceptionHandler();

    private static final HttpHandlerStrategyContext context = new HttpHandlerStrategyContext(
            ControllerConfig.getAuthorizationController(),
            ControllerConfig.getRegistrationController()
    );

    private static final HttpHandlerStrategyMap strategyMap = new HttpHandlerStrategyMap(context);

    private static final HttpHandler httpHandler = new HttpHandler(
            strategyMap,
            exceptionHandler
    );

    public static HttpHandler getHttpHandler() {
        return httpHandler;
    }

}
