package com.github.messenger.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.util.List;
import java.util.concurrent.Future;

public class Broker {

    private static final Logger log = LoggerFactory.getLogger(Broker.class);

    public void broadcast(List<Session> sessions, String payload) {

        sessions.forEach(session -> {
            Future<Void> deliveryTracker = session.getAsyncRemote().sendText(payload);
            deliveryTracker.isDone();
        });
    }

    public void send(Session session, String payload) {
        session.getAsyncRemote().sendText(payload);
    }

}
