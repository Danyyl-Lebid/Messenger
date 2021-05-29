package com.github.messenger.controllers.message;

import javax.websocket.Session;

public interface IMessageController {

    void sendHistory(Session session, Long chatId);

    void broadcast(String payload);

    void save(Long userId, String payload);

}
