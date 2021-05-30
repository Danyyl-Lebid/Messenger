package com.github.messenger.controllers.message;

import com.github.messenger.payload.Envelope;

import javax.websocket.Session;

public interface IMessageController {

    void sendHistory(Session session, Long chatId);

    void broadcast(Envelope envelope);

    void save(Long userId, String payload);

}
