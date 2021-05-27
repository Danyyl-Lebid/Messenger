package com.github.messenger.config;

import com.github.messenger.entity.Chat;
import com.github.messenger.entity.GlobalMessage;
import com.github.messenger.entity.User;
import com.github.messenger.entity.UserChatRelation;
import com.github.messenger.repository.IRepository;
import com.github.messenger.repository.impl.HibernateRepository;
import com.github.messenger.utils.HibernateSessionManager;

public class RepositoryConfig {

    private static final HibernateSessionManager hibernateSessionManager = new HibernateSessionManager();

    private static final IRepository<User> userRepository = new HibernateRepository<>(User.class, hibernateSessionManager);

    private static final IRepository<GlobalMessage> globalMessageRepository = new HibernateRepository<>(GlobalMessage.class, hibernateSessionManager);

    private static final IRepository<Chat> chatRepository = new HibernateRepository<>(Chat.class, hibernateSessionManager);

    private static final IRepository<UserChatRelation> userChatRelationRepository = new HibernateRepository<>(UserChatRelation.class, hibernateSessionManager);

    public static IRepository<User> getUserRepository() {
        return userRepository;
    }

    public static IRepository<GlobalMessage> getGlobalMessageRepository() {
        return globalMessageRepository;
    }

    public static IRepository<Chat> getChatRepository() {
        return chatRepository;
    }

    public static IRepository<UserChatRelation> getUserChatRelationRepository() {
        return userChatRelationRepository;
    }
}
