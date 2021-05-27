package com.github.messenger.controllers.message.impl;

import com.github.messenger.controllers.message.IMessageController;
import com.github.messenger.dto.message.MessageDto;
import com.github.messenger.entity.Message;
import com.github.messenger.exceptions.IncorrectPayload;
import com.github.messenger.network.Broker;
import com.github.messenger.network.RoomConnectionPools;
import com.github.messenger.service.message.IMessageService;
import com.github.messenger.utils.JsonHelper;

import javax.websocket.Session;

public class MessageController implements IMessageController {

    private final IMessageService messageService;

    private final RoomConnectionPools roomConnectionPools;

    private final Broker broker;

    public MessageController(IMessageService messageService, RoomConnectionPools roomConnectionPools, Broker broker) {
        this.messageService = messageService;
        this.roomConnectionPools = roomConnectionPools;
        this.broker = broker;
    }

    @Override
    public void sendHistory(Session session) {

    }

    @Override
    public void broadcast(Long chatId, String payload) {
        broker.broadcast(roomConnectionPools.getConnectionPool(chatId).getSessions(), payload);
    }

    @Override
    public void save(Long userId, String payload) {
        MessageDto dto = JsonHelper.fromJson(payload, MessageDto.class).orElseThrow(IncorrectPayload::new);
        Message message = new Message(
                null,
                userId,
                dto.getChatId(),
                dto.getText(),
                dto.getTime()
        );
        messageService.save(message);
    }
}