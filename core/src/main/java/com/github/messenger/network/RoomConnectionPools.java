package com.github.messenger.network;

import com.github.messenger.entity.Chat;
import com.github.messenger.service.chat.IChatService;
import com.github.messenger.service.chat.IUserChatRelationService;

import javax.websocket.Session;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomConnectionPools {

    private final Map<Long, WebsocketConnectionPool> connectionPoolMap;

    private final IUserChatRelationService userChatRelationService;

    public RoomConnectionPools(IChatService chatService, IUserChatRelationService userChatRelationService) {
        this.userChatRelationService = userChatRelationService;
        this.connectionPoolMap = new HashMap<>();
        Collection<Chat> chats = chatService.findAll();
        for (Chat chat: chats) {
            this.connectionPoolMap.put(chat.getId(), new WebsocketConnectionPool());
        }
    }

    public void addSession(Long userId, Session session){
        List<Long> chatsId = userChatRelationService.findAllChatsByUserId(userId);
        for (Long chatId: chatsId) {
            connectionPoolMap.get(chatId).addSession(userId, session);
        }
    }

    public void removeSession(Long userId){
        List<Long> chatsId = userChatRelationService.findAllChatsByUserId(userId);
        for (Long chatId: chatsId) {
            connectionPoolMap.get(chatId).removeSession(userId);
        }
    }

    public WebsocketConnectionPool getConnectionPool(Long chatId){
        return connectionPoolMap.get(chatId);
    }

    public Map<Long, WebsocketConnectionPool> getConnectionPoolMap() {
        return connectionPoolMap;
    }

    public void addConnectionPool(Long chatId){
        connectionPoolMap.put(chatId, new WebsocketConnectionPool());
    }
}
