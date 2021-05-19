package com.github.messenger.network;

import com.github.messenger.payload.Envelope;
import com.github.messenger.utils.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

public class Broker {

    private static final Logger log = LoggerFactory.getLogger(Broker.class);

    public void broadcast(List<Session> sessions, Envelope payload) {
        String str = JsonHelper.toJson(payload).orElseThrow();

        sessions.forEach(session -> {
            Future<Void> deliveryTracker = session.getAsyncRemote().sendText(str);
            deliveryTracker.isDone();
        });
    }

    public void send(Session session, Envelope payload) {
        try {
            session.getBasicRemote().sendText(JsonHelper.toJson(payload).orElseThrow(IOException::new));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
