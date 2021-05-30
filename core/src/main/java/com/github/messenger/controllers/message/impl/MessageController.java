package com.github.messenger.controllers.message.impl;

import com.github.messenger.controllers.message.IMessageController;
import com.github.messenger.dto.message.MessageDto;
import com.github.messenger.entity.Message;
import com.github.messenger.exceptions.IncorrectPayload;
import com.github.messenger.network.Broker;
import com.github.messenger.network.RoomConnectionPools;
import com.github.messenger.payload.Envelope;
import com.github.messenger.payload.Topic;
import com.github.messenger.service.message.IMessageService;
import com.github.messenger.utils.JsonHelper;

import javax.websocket.Session;
import java.util.Collection;
import java.util.Date;

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
    public void sendHistory(Session session, Long chatId) {
        Collection<Message> messages = messageService.findAllByChatId(chatId);
        for(Message message : messages){
            MessageDto dto = new MessageDto(
                    message.getChatId(),
                    message.getNickname(),
                    message.getText(),
                    new Date(message.getTime())
            );
            String payload = JsonHelper.toJson(dto).orElseThrow();
            Envelope envelope = new Envelope(Topic.MESSAGE, "empty-token", payload);
            broker.send(session, JsonHelper.toJson(envelope).orElseThrow());
        }
    }

    @Override
    public void broadcast(Envelope envelope) {
        MessageDto dto = JsonHelper.fromJson(envelope.getPayload(), MessageDto.class).orElseThrow(IncorrectPayload::new);
        broker.broadcast(roomConnectionPools.getConnectionPool(dto.getChatId()).getSessions(), JsonHelper.toJson(envelope).orElseThrow());
    }

    @Override
    public void save(Long userId, String payload) {
        System.out.println(payload);
        MessageDto dto = JsonHelper.fromJson(payload, MessageDto.class).orElseThrow(IncorrectPayload::new);
        Message message = new Message(
                null,
                userId,
                dto.getChatId(),
                dto.getNickname(),
                dto.getText(),
                dto.getTime().getTime()
        );
        messageService.save(message);
    }
}
