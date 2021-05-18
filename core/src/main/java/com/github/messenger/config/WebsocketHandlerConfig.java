package com.github.messenger.config;

import com.github.messenger.handlers.WebsocketHandler;

public class WebsocketHandlerConfig {

    private static WebsocketHandler websocketHandler = new WebsocketHandler();

    public static WebsocketHandler getWebsocketHandler() {
        return websocketHandler;
    }
}
