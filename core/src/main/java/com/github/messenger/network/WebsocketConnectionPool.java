package com.github.messenger.network;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class WebsocketConnectionPool {

    private final ConcurrentHashMap<Long, Session> sessions = new ConcurrentHashMap<>();

    public void addSession(Long id, Session session) {
        this.sessions.put(id, session);
    }

    public void removeSession(Long id) {
        this.sessions.remove(id);
    }

    public List<Session> getSessions() {
        return new ArrayList<>(sessions.values());
    }

    public Session getSession(Long id) {
        return this.sessions.get(id);
    }

}
