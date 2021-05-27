package com.github.messenger.service.chat.impl;

import com.github.messenger.entity.Chat;
import com.github.messenger.entity.User;
import com.github.messenger.entity.UserChatRelation;
import com.github.messenger.payload.Role;
import com.github.messenger.payload.Status;
import com.github.messenger.repository.IRepository;
import com.github.messenger.repository.impl.HibernateRepository;
import com.github.messenger.service.chat.IUserChatRelationService;
import com.github.messenger.utils.HibernateSessionManager;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class UserChatRelationServiceTest {

    private static final int mockSize = 6;

    private static final HibernateSessionManager sessionManager = new HibernateSessionManager();

    private static final IRepository<User> userRepository = new HibernateRepository<>(User.class, sessionManager);

    private static final IRepository<Chat> chatRepository = new HibernateRepository<>(Chat.class, sessionManager);

    private static final IRepository<UserChatRelation> relationRepository = new HibernateRepository<>(UserChatRelation.class, sessionManager);

    private static final IUserChatRelationService relationService = new UserChatRelationService(relationRepository);

    private final User mockUser = new User(null, "firstName_mock", "lastName_mock", "login_mock", "password_mock", "email_mock", "nickname_mock", "phone_mock", Role.ADMIN, Status.OFFLINE, 0L);

    private final User[] mockUserArray = new User[]{
            new User(null, "firstName_1", "lastName_1", "login_1", "password_1", "email_1", "nickname_1", "phone_1", Role.USER, Status.OFFLINE, 0L),
            new User(null, "firstName_2", "lastName_2", "login_2", "password_2", "email_2", "nickname_2", "phone_2", Role.USER, Status.OFFLINE, 0L),
            new User(null, "firstName_3", "lastName_3", "login_3", "password_3", "email_3", "nickname_3", "phone_3", Role.USER, Status.OFFLINE, 0L),
            new User(null, "firstName_4", "lastName_4", "login_4", "password_4", "email_4", "nickname_4", "phone_4", Role.USER, Status.OFFLINE, 0L),
            new User(null, "firstName_5", "lastName_5", "login_5", "password_5", "email_5", "nickname_5", "phone_5", Role.USER, Status.OFFLINE, 0L),
            new User(null, "firstName_6", "lastName_6", "login_6", "password_6", "email_6", "nickname_6", "phone_6", Role.USER, Status.OFFLINE, 0L)
    };

    private final Chat mockChat = new Chat(null, "chat_mock");

    private final Chat[] mockChatArray = new Chat[]{
            new Chat(null, "chat_1"),
            new Chat(null, "chat_2"),
            new Chat(null, "chat_3"),
            new Chat(null, "chat_4"),
            new Chat(null, "chat_5"),
            new Chat(null, "chat_6"),
    };

    private final UserChatRelation mockRelation = new UserChatRelation(null, 0L, 0L);

    private final UserChatRelation[] mockRelationArray = new UserChatRelation[]{
            new UserChatRelation(), new UserChatRelation(), new UserChatRelation(),
            new UserChatRelation(), new UserChatRelation(), new UserChatRelation()
    };

    @Before
    public void setUp() {
        for (User user : mockUserArray) {
            userRepository.save(user);
        }
        for (Chat chat : mockChatArray) {
            chatRepository.save(chat);
        }
        for (int i = 0; i < mockSize; i++) {
            mockRelationArray[i].setUserId(mockUserArray[i].getId());
            mockRelationArray[i].setChatId(mockChatArray[i].getId());
        }
        for (UserChatRelation relation : mockRelationArray) {
            relationRepository.save(relation);
        }
    }

    @After
    public void tearDown() {
        Collection<User> users = userRepository.findAll();
        for (User user : users) {
            userRepository.delete(user);
        }
        Collection<Chat> chats = chatRepository.findAll();
        for (Chat chat : chats) {
            chatRepository.delete(chat);
        }
        Collection<UserChatRelation> relations = relationRepository.findAll();
        for (UserChatRelation relation : relations) {
            relationRepository.delete(relation);
        }
    }

    @Test
    public void addRelation() {
        UserChatRelation exp = new UserChatRelation(null, mockUserArray[0].getId(), mockChatArray[1].getId());
        relationService.addRelation(exp);
        UserChatRelation act = relationRepository.findById(exp.getId());
        Assert.assertEquals(exp, act);
    }

    @Test (expected = ConstraintViolationException.class)
    public void addRelationNoUser() {
        relationService.addRelation(new UserChatRelation(null, 0L, mockChatArray[1].getId()));
    }

    @Test (expected = ConstraintViolationException.class)
    public void addRelationNoChat() {
        relationService.addRelation(new UserChatRelation(null, mockUserArray[0].getId(), 0L));
    }

    @Test (expected = ConstraintViolationException.class)
    public void addRelationNullUser() {
        relationService.addRelation(new UserChatRelation(null, null, mockChatArray[1].getId()));
    }

    @Test (expected = ConstraintViolationException.class)
    public void addRelationNullChat() {
        relationService.addRelation(new UserChatRelation(null, mockUserArray[0].getId(), null));
    }

    @Test
    public void removeRelation() {
        UserChatRelation relation = new UserChatRelation(null, mockUserArray[0].getId(), mockChatArray[1].getId());
        relationService.addRelation(relation);
        relationService.removeRelation(relation);
        Collection<UserChatRelation> exp = new ArrayList<>(Arrays.asList(mockRelationArray));
        Collection<UserChatRelation> act = relationRepository.findAll();
        Assert.assertTrue(collectionsEqualsInAnyOrder(exp, act));
    }

    @Test
    public void findAllUsersByChatIdOne(){
        Collection<Long> exp = new ArrayList<>();
        exp.add(mockRelationArray[0].getUserId());
        Collection<Long> act = relationService.findAllUsersByChatId(mockChatArray[0].getId());
        Assert.assertTrue(collectionsEqualsInAnyOrder(exp, act));
    }

    @Test
    public void findAllUsersByChatIdTwo(){
        UserChatRelation newRelation = new UserChatRelation(null, mockUserArray[1].getId(), mockChatArray[0].getId());
        relationService.addRelation(newRelation);
        Collection<Long> exp = new ArrayList<>();
        exp.add(mockRelationArray[0].getUserId());
        exp.add(newRelation.getUserId());
        Collection<Long> act = relationService.findAllUsersByChatId(mockChatArray[0].getId());
        Assert.assertTrue(collectionsEqualsInAnyOrder(exp, act));
    }


    private <T> boolean collectionsEqualsInAnyOrder(Collection<T> first, Collection<T> second) {
        return first.size() == second.size() && first.containsAll(second) && second.containsAll(first);
    }


}