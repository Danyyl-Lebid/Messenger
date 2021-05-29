package com.github.messenger.handlers;

import com.github.messenger.controllers.chat.IChatController;
import com.github.messenger.controllers.global.message.IGlobalMessageController;
import com.github.messenger.controllers.message.IMessageController;
import com.github.messenger.controllers.status.IStatusController;
import com.github.messenger.dto.HistoryRequestDto;
import com.github.messenger.exceptions.ExpiredToken;
import com.github.messenger.network.Broker;
import com.github.messenger.network.RoomConnectionPools;
import com.github.messenger.network.WebsocketConnectionPool;
import com.github.messenger.payload.Envelope;
import com.github.messenger.payload.PrivateToken;
import com.github.messenger.utils.JsonHelper;
import com.github.messenger.utils.PrivateTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import java.util.Objects;

public class WebsocketHandler {

    private static final Logger log = LoggerFactory.getLogger(WebsocketHandler.class);

    private final WebsocketConnectionPool globalConnectionPool;

    private final RoomConnectionPools roomConnectionPools;

    private final IGlobalMessageController globalMessageController;

    private final IMessageController messageController;

    private final IStatusController statusController;

    private final IChatController chatController;

    private final Broker broker;

    public WebsocketHandler(
            WebsocketConnectionPool globalConnectionPool,
            RoomConnectionPools roomConnectionPools,
            Broker broker,
            IGlobalMessageController globalMessageController,
            IMessageController messageController,
            IStatusController statusController,
            IChatController chatController
    ) {
        this.globalConnectionPool = globalConnectionPool;
        this.roomConnectionPools = roomConnectionPools;
        this.broker = broker;
        this.globalMessageController = globalMessageController;
        this.messageController = messageController;
        this.statusController = statusController;
        this.chatController = chatController;
    }

    @OnMessage
    public void messages(Session session, String payload) {
        try {
            Envelope envelope = JsonHelper.fromJson(payload, Envelope.class).orElseThrow();
            PrivateToken result = PrivateTokenProvider.decode(envelope.getToken());
            if (!PrivateTokenProvider.validateToken(result)) {
                throw new ExpiredToken();
            }
            envelope.setToken("empty-token");
            String resultString;
            switch (envelope.getTopic()) {
                case LOGIN:
                    globalConnectionPool.addSession(result.getUserId(), session);
                    roomConnectionPools.addSession(result.getUserId(), session);
                    statusController.setOnline(result.getUserId());
                    resultString = JsonHelper.toJson(envelope).orElseThrow();
                    broker.broadcast(globalConnectionPool.getSessions(), resultString);
                    break;
                case GLOBAL_MESSAGE:
                    if (Objects.isNull(this.globalMessageController)) {
                        System.out.println("Is null in websocket handler");
                    }
                    this.globalMessageController.save(result.getUserId(), envelope.getPayload());
                    resultString = JsonHelper.toJson(envelope).orElseThrow();
                    this.globalMessageController.broadcast(resultString);
                    break;
                case MESSAGE:
                    messageController.save(result.getUserId(), envelope.getPayload());
                    resultString = JsonHelper.toJson(envelope).orElseThrow();
                    messageController.broadcast(resultString);
                    break;
                case CHAT_HISTORY:
                    HistoryRequestDto historyRequestDto = JsonHelper.fromJson(envelope.getPayload(), HistoryRequestDto.class).orElseThrow();
                    messageController.sendHistory(session, historyRequestDto.getChatId());
                    break;
                case GLOBAL_HISTORY:
                    globalMessageController.sendHistory(session);
                    break;
                case CREATE_CHAT:
                    chatController.createChat(envelope.getPayload());
                    break;
                case NICKNAMES:
                    resultString = chatController.getAllUsers();
                    broker.send(session, resultString);
                    break;
                case PARTICIPANTS:
                    resultString = chatController.getParticipants(envelope.getPayload());
                    broker.send(session, resultString);
                    break;
                case CHATS:
                    resultString = chatController.getChats(result.getUserId());
                    broker.send(session, resultString);
                    break;
                case LOGOUT:
                    globalConnectionPool.removeSession(result.getUserId());
                    roomConnectionPools.removeSession(result.getUserId());
                    statusController.setOffline(result.getUserId());
                    resultString = JsonHelper.toJson(envelope).orElseThrow();
                    broker.broadcast(globalConnectionPool.getSessions(), resultString);
                    break;
            }
        } catch (Throwable e) {
            broker.send(session, String.format("Error %s has occurred on server", e.getClass().getName()));
            log.warn("Enter {}", e.getMessage());
        }
    }
}
