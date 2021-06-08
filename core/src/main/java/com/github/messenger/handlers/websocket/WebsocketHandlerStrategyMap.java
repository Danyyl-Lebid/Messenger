package com.github.messenger.handlers.websocket;

import com.github.messenger.dto.HistoryRequestDto;
import com.github.messenger.payload.Envelope;
import com.github.messenger.payload.Token;
import com.github.messenger.payload.Topic;
import com.github.messenger.utils.JsonHelper;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

public class WebsocketHandlerStrategyMap {

    private final WebsocketHandlerStrategyContext context;

    private final Map<Topic, IWebsocketHandlerStrategy> map = new HashMap<>();

    public WebsocketHandlerStrategyMap(WebsocketHandlerStrategyContext context) {
        this.context = context;
    }

    private void initializeMap(){
        this.map.put(Topic.LOGIN, loginStrategy);
        this.map.put(Topic.GLOBAL_MESSAGE, globalMessageStrategy);
        this.map.put(Topic.MESSAGE, messageStrategy);
        this.map.put(Topic.CHAT_HISTORY, chatHistoryStrategy);
        this.map.put(Topic.GLOBAL_HISTORY, globalHistoryStrategy);
        this.map.put(Topic.CREATE_CHAT, createChatStrategy);
        this.map.put(Topic.NICKNAMES, nicknamesStrategy);
        this.map.put(Topic.PARTICIPANTS, participantsStrategy);
        this.map.put(Topic.CHATS, chatsStrategy);
        this.map.put(Topic.LOGOUT, logoutStrategy);
    }

    private final IWebsocketHandlerStrategy loginStrategy = new IWebsocketHandlerStrategy() {
        @Override
        public void doHandle(Session session, Envelope envelope, Token token) {
            context.getGlobalConnectionPool().addSession(token.getUserId(), session);
            context.getRoomConnectionPools().addSession(token.getUserId(), session);
            context.getStatusController().setOnline(token.getUserId());
            String response = JsonHelper.toJson(envelope).orElseThrow();
            context.getBroker().broadcast(context.getGlobalConnectionPool().getSessions(), response);
        }
    };

    private final IWebsocketHandlerStrategy globalMessageStrategy = new IWebsocketHandlerStrategy() {
        @Override
        public void doHandle(Session session, Envelope envelope, Token token) {
            context.getGlobalMessageController().save(token.getUserId(), envelope.getPayload());
            String response = JsonHelper.toJson(envelope).orElseThrow();
            context.getGlobalMessageController().broadcast(response);
        }
    };

    private final IWebsocketHandlerStrategy messageStrategy = new IWebsocketHandlerStrategy() {
        @Override
        public void doHandle(Session session, Envelope envelope, Token token) {
            context.getMessageController().save(token.getUserId(), envelope.getPayload());
            context.getMessageController().broadcast(envelope);
        }
    };

    private final IWebsocketHandlerStrategy chatHistoryStrategy = new IWebsocketHandlerStrategy() {
        @Override
        public void doHandle(Session session, Envelope envelope, Token token) {
            HistoryRequestDto historyRequestDto = JsonHelper.fromJson(envelope.getPayload(), HistoryRequestDto.class).orElseThrow();
            context.getMessageController().sendHistory(session, historyRequestDto.getChatId());
        }
    };

    private final IWebsocketHandlerStrategy globalHistoryStrategy = new IWebsocketHandlerStrategy() {
        @Override
        public void doHandle(Session session, Envelope envelope, Token token) {
            context.getGlobalMessageController().sendHistory(session);
        }
    };

    private final IWebsocketHandlerStrategy createChatStrategy = new IWebsocketHandlerStrategy() {
        @Override
        public void doHandle(Session session, Envelope envelope, Token token) {
            context.getChatController().createChat(envelope.getPayload());
        }
    };

    private final IWebsocketHandlerStrategy nicknamesStrategy = new IWebsocketHandlerStrategy() {
        @Override
        public void doHandle(Session session, Envelope envelope, Token token) {
            String response = context.getChatController().getAllUsers();
            context.getBroker().sendAsync(session, response);
        }
    };

    private final IWebsocketHandlerStrategy participantsStrategy = new IWebsocketHandlerStrategy() {
        @Override
        public void doHandle(Session session, Envelope envelope, Token token) {
            String response = context.getChatController().getParticipants(envelope.getPayload());
            context.getBroker().sendAsync(session, response);
        }
    };

    private final IWebsocketHandlerStrategy chatsStrategy = new IWebsocketHandlerStrategy() {
        @Override
        public void doHandle(Session session, Envelope envelope, Token token) {
            String response = context.getChatController().getChats(token.getUserId());
            context.getBroker().sendAsync(session, response);
        }
    };

    private final IWebsocketHandlerStrategy logoutStrategy = new IWebsocketHandlerStrategy() {
        @Override
        public void doHandle(Session session, Envelope envelope, Token token) {
            context.getGlobalConnectionPool().removeSession(token.getUserId());
            context.getRoomConnectionPools().removeSession(token.getUserId());
            context.getStatusController().setOffline(token.getUserId());
            String response = JsonHelper.toJson(envelope).orElseThrow();
            context.getBroker().broadcast(context.getGlobalConnectionPool().getSessions(), response);
        }
    };

    public Map<Topic, IWebsocketHandlerStrategy> getMap() {
        return map;
    }
}
