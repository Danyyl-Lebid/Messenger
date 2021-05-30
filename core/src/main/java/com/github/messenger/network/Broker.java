package com.github.messenger.network;

import com.github.messenger.utils.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.IOException;
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

    public void sendAsync(Session session, String payload) {
        session.getAsyncRemote().sendText(payload);
    }

    public void sendBasic(Session session, String payload){
        try {
            session.getBasicRemote().sendText(JsonHelper.toJson(payload).orElseThrow(IOException::new));
        } catch (IOException e) {
            log.error(String.format("%s - Message: %s", e.getClass().getName(), e.getMessage()));
        }
    }

}
