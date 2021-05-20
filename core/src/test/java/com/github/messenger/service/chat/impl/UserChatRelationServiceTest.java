package com.github.messenger.service.chat.impl;

import com.github.messenger.entity.Chat;
import com.github.messenger.entity.User;
import com.github.messenger.entity.UserChatRelation;
import com.github.messenger.repository.IRepository;
import com.github.messenger.repository.impl.HibernateRepository;
import com.github.messenger.service.chat.IUserChatRelationService;
import com.github.messenger.utils.HibernateSessionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserChatRelationServiceTest {

    private static final HibernateSessionManager sessionManager = new HibernateSessionManager();

    private static final IRepository<User> userRepository = new HibernateRepository<>(User.class, sessionManager);

    private static final IRepository<Chat> chatService = new HibernateRepository<>(Chat.class, sessionManager);

    private static final IRepository<UserChatRelation> relationRepository = new HibernateRepository<>(UserChatRelation.class, sessionManager);

    private static final IUserChatRelationService relationService = new UserChatRelationService(relationRepository);

    private final UserChatRelation mockRelation = new UserChatRelation(1L, 1L, 1L);

    private final User mockUser = new User();

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void addUser(){
    }
}