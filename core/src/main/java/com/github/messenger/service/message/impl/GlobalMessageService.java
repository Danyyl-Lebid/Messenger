package com.github.messenger.service.message.impl;

import com.github.messenger.entity.GlobalMessage;
import com.github.messenger.repository.IRepository;
import com.github.messenger.service.message.IGlobalMessageService;

import java.util.Collection;

public class GlobalMessageService implements IGlobalMessageService {

    private final IRepository<GlobalMessage> repository;

    public GlobalMessageService(IRepository<GlobalMessage> repository) {
        this.repository = repository;
    }

    @Override
    public GlobalMessage save(GlobalMessage message) {
        repository.save(message);
        return message;
    }

    @Override
    public Collection<GlobalMessage> getMessages() {
        return repository.findAll();
    }
}
