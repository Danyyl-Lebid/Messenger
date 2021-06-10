package com.github.messenger.handlers.websocket;

import com.github.messenger.payload.Envelope;
import com.github.messenger.payload.Token;

import javax.websocket.Session;

public interface IWebsocketHandlerStrategy {

    void doHandle(Session session, Envelope envelope, Token token);

}
