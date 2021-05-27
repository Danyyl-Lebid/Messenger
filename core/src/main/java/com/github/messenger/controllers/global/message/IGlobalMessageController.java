package com.github.messenger.controllers.global.message;

import javax.websocket.Session;

public interface IGlobalMessageController {

    void sendHistory(Session session);

    void broadcast(String payload);

    void save(Long userId, String payload);

}
