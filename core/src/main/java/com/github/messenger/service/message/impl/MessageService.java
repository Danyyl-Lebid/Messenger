package com.github.messenger.service.message.impl;

import com.github.messenger.entity.Message;
import com.github.messenger.repository.IRepository;
import com.github.messenger.service.message.IMessageService;

import java.util.Collection;

public class MessageService implements IMessageService {

    private final IRepository<Message> messageRepository;

    public MessageService(IRepository<Message> messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message save(Message message) {
        messageRepository.save(message);
        return message;
    }

    @Override
    public Collection<Message> findAllByChatId(Long chatId) {
        return messageRepository.findAllBy("chat_id", Long.class, chatId);
    }
}
