package com.github.messenger.service.message;

import com.github.messenger.entity.Message;

import java.util.Collection;

public interface IMessageService {

    Message save(Message message);

    Collection<Message> findAllByChatId(Long chatId);

}
