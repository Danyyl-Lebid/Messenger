package com.github.messenger.network;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class WebsocketConnectionPool {

    private ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();

    public void addSession(String login, Session session) {
        this.sessions.put(login, session);
    }

    public void removeSession(String login) {
        this.sessions.remove(login);
    }

    public List<Session> getSessions() {
        return new ArrayList<>(sessions.values());
    }

    public Session getSession(String login) {
        return this.sessions.get(login);
    }

}
