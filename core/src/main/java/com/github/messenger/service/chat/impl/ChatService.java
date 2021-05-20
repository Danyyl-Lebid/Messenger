package com.github.messenger.service.chat.impl;

import com.github.messenger.entity.Chat;
import com.github.messenger.repository.IRepository;
import com.github.messenger.service.chat.IChatService;

import java.util.Collection;

public class ChatService implements IChatService {

    private final IRepository<Chat> chatRepository;

    public ChatService(IRepository<Chat> chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Chat create(Chat chat) {
        chatRepository.save(chat);
        return chat;
    }

    @Override
    public Chat findById(Long id) {
        return chatRepository.findById(id);
    }

    @Override
    public Collection<Chat> findByName(String name) {
        return chatRepository.findAllBy("name", name);
    }

    @Override
    public void update(Chat chat) {
        chatRepository.update(chat);
    }
}
