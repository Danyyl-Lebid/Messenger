package com.github.messenger.service.message.impl;

import com.github.messenger.entity.LastMessage;
import com.github.messenger.entity.Message;
import com.github.messenger.entity.UserChatRelation;
import com.github.messenger.repository.IRepository;
import com.github.messenger.service.message.IMessageService;

import java.util.Collection;

public class MessageService implements IMessageService {

    private final IRepository<Message> messageRepository;

    private final IRepository<LastMessage> lastMessageRepository;

    private final IRepository<UserChatRelation> userChatRepository;

    public MessageService(IRepository<Message> messageRepository, IRepository<LastMessage> lastMessageRepository, IRepository<UserChatRelation> userChatRepository) {
        this.messageRepository = messageRepository;
        this.lastMessageRepository = lastMessageRepository;
        this.userChatRepository = userChatRepository;
    }

    @Override
    public Message save(Message message) {
        messageRepository.save(message);
        UserChatRelation userChatRelation = userChatRepository.findById(message.getUserChatId());
        LastMessage lastMessage = lastMessageRepository.findBy("chat_id", userChatRelation.getChatId());
        lastMessageRepository.delete(lastMessage);
        lastMessageRepository.save(
                new LastMessage(
                        null,
                        message.getId(),
                        userChatRelation.getChatId()
                )
        );
        return message;
    }

    @Override
    public Collection<Message> findAllByChatId(Long chatId) {
        return messageRepository.findAllBy("chat_id", chatId);
    }

    @Override
    public Message findLastByChatId(Long chatId) {
        LastMessage lastMessage = lastMessageRepository.findBy("chat_id", chatId);
        return messageRepository.findById(lastMessage.getMessageId());
    }
}
