package com.github.messenger.service.chat.impl;

import com.github.messenger.entity.Chat;
import com.github.messenger.exceptions.Conflict;
import com.github.messenger.exceptions.UpdateError;
import com.github.messenger.repository.IRepository;
import com.github.messenger.service.chat.IChatService;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.PersistenceException;
import java.util.Collection;

public class ChatService implements IChatService {

    private final IRepository<Chat> chatRepository;

    public ChatService(IRepository<Chat> chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Collection<Chat> findAll() {
        return chatRepository.findAll();
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
    public void create(Chat chat) {
        try {
            chatRepository.save(chat);
        } catch (ConstraintViolationException e) {
            throw new Conflict();
        }
    }

    @Override
    public void update(Chat chat) {
        try {
            chatRepository.update(chat);
        } catch (PersistenceException e) {
            throw new UpdateError();
        }
    }
}
