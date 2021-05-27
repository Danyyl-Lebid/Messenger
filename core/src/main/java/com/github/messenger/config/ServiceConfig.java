package com.github.messenger.config;

import com.github.messenger.service.chat.IChatService;
import com.github.messenger.service.chat.IUserChatRelationService;
import com.github.messenger.service.chat.impl.ChatService;
import com.github.messenger.service.chat.impl.UserChatRelationService;
import com.github.messenger.service.message.IGlobalMessageService;
import com.github.messenger.service.message.IMessageService;
import com.github.messenger.service.message.impl.GlobalMessageService;
import com.github.messenger.service.message.impl.MessageService;
import com.github.messenger.service.user.IUserService;
import com.github.messenger.service.user.impl.UserService;


public class ServiceConfig {

    private static final IUserService userService = new UserService(RepositoryConfig.getUserRepository());

    private static final IGlobalMessageService globalMessageService = new GlobalMessageService(RepositoryConfig.getGlobalMessageRepository());

    private static final IMessageService messageService = new MessageService(RepositoryConfig.getMessageRepository());

    private static final IChatService chatService = new ChatService(RepositoryConfig.getChatRepository());

    private static final IUserChatRelationService userChatRelationService = new UserChatRelationService(RepositoryConfig.getUserChatRelationRepository());

    public static IUserService getUserService() {
        return userService;
    }

    public static IGlobalMessageService getGlobalMessageService() {
        return globalMessageService;
    }

    public static IMessageService getMessageService() {
        return messageService;
    }

    public static IChatService getChatService() {
        return chatService;
    }

    public static IUserChatRelationService getUserChatRelationService() {
        return userChatRelationService;
    }
}
