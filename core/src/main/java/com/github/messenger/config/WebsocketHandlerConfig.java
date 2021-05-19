package com.github.messenger.config;

import com.github.messenger.handlers.WebsocketHandler;
import com.github.messenger.network.Broker;
import com.github.messenger.network.WebsocketConnectionPool;

public class WebsocketHandlerConfig {

    private static final WebsocketConnectionPool websocketConnectionPool = new WebsocketConnectionPool();

    private static final Broker broker = new Broker();

    private static final WebsocketHandler websocketHandler = new WebsocketHandler(getWebsocketConnectionPool(), getBroker());

    public static WebsocketHandler getWebsocketHandler() {
        return websocketHandler;
    }

    public static WebsocketConnectionPool getWebsocketConnectionPool() {
        return websocketConnectionPool;
    }

    public static Broker getBroker() {
        return broker;
    }
}
