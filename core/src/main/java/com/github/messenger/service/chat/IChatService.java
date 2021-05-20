package com.github.messenger.service.chat;

import com.github.messenger.entity.Chat;
import com.github.messenger.entity.User;

import java.util.Collection;
import java.util.List;

public interface IChatService {

    Collection<Chat> findAll();

    Chat findById(Long id);

    Collection<Chat> findByName(String name);

    void create(Chat chat);

    void update(Chat chat);

}
