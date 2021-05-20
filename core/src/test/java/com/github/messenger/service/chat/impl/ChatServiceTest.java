package com.github.messenger.service.chat.impl;

import com.github.messenger.entity.Chat;
import com.github.messenger.exceptions.UpdateError;
import com.github.messenger.repository.IRepository;
import com.github.messenger.repository.impl.HibernateRepository;
import com.github.messenger.service.chat.IChatService;
import com.github.messenger.utils.HibernateSessionManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ChatServiceTest {

    private static IRepository<Chat> repository = new HibernateRepository<>(Chat.class, new HibernateSessionManager());

    private static IChatService service = new ChatService(repository);

    private static final Chat mockChat = new Chat(null, "mockChat");

    private static Chat[] mockChatArray = new Chat[]{
            new Chat(null, "chat_1"),
            new Chat(null, "chat_2"),
            new Chat(null, "chat_3"),
            new Chat(null, "chat_4"),
            new Chat(null, "chat_5"),
            new Chat(null, "chat_6"),
    };

    @Before
    public void setUp() {
        for(Chat chat: mockChatArray){
            repository.save(chat);
        }
    }

    @After
    public void tearDown() {
        Collection<Chat> chats = repository.findAll();
        for(Chat chat : chats){
            repository.delete(chat);
        }
    }

    @Test
    public void findAll(){
        Collection<Chat> exp = Arrays.asList(mockChatArray);
        Collection<Chat> act = service.findAll();
        Assert.assertTrue(collectionsEqualsInAnyOrder(exp, act));
    }

    @Test
    public void findAllEmpty(){
        for(Chat chat : mockChatArray){
            repository.delete(chat);
        }
        Collection<Chat> exp = new ArrayList<>();
        Collection<Chat> act = service.findAll();
        Assert.assertTrue(collectionsEqualsInAnyOrder(exp, act));
    }

    @Test
    public void findById(){
        Chat exp = mockChatArray[0];
        Chat act = service.findById(mockChatArray[0].getId());
        Assert.assertEquals(exp, act);
    }

    @Test
    public void findByIdNotPresented() {
        Assert.assertNull(service.findById(1L));
    }

    @Test
    public void findByIdNegative() {
        Assert.assertNull(service.findById(-1L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void findByIdNull() {
        Assert.assertNull(service.findById(null));
    }

    @Test
    public void findAllByName(){
        Collection<Chat> exp = new ArrayList<>();
        exp.add(mockChatArray[0]);
        Collection<Chat> act = service.findByName("chat_1");
        Assert.assertTrue(collectionsEqualsInAnyOrder(exp, act));
    }

    @Test
    public void findAllByNameNotPresented(){
        Collection<Chat> exp = new ArrayList<>();
        Collection<Chat> act = service.findByName("not_presented");
        Assert.assertTrue(collectionsEqualsInAnyOrder(exp, act));
    }

    @Test
    public void findAllByNameNull(){
        Collection<Chat> exp = new ArrayList<>();
        Collection<Chat> act = service.findByName(null);
        Assert.assertEquals(exp, act);
    }

    @Test
    public void create(){
        service.create(mockChat);
        Chat act = service.findById(mockChat.getId());
        Assert.assertEquals(mockChat, act);
    }

    @Test (expected = IllegalArgumentException.class)
    public void createNull(){
        service.create(null);
    }

    @Test
    public void update(){
        service.create(mockChat);
        mockChat.setName("new_name");
        service.update(mockChat);
        Chat act = service.findById(mockChat.getId());
        Assert.assertEquals(mockChat, act);
        mockChat.setName("mockChat");
    }

    @Test (expected = UpdateError.class)
    public void updateNullId(){
        service.update(new Chat(null, "chat_name"));
    }

    @Test (expected = UpdateError.class)
    public void updateNotPresented(){
        service.update(new Chat(0L, "chat_name"));
    }

    private <T> boolean  collectionsEqualsInAnyOrder(Collection<T> first, Collection<T> second) {
        return first.size() == second.size() && first.containsAll(second) && second.containsAll(first);
    }

}