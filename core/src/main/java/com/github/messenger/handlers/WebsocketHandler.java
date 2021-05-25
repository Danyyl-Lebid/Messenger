package com.github.messenger.handlers;

import com.github.messenger.dto.message.GlobalMessageDto;
import com.github.messenger.entity.GlobalMessage;
import com.github.messenger.exceptions.ExpiredToken;
import com.github.messenger.exceptions.IncorrectPayload;
import com.github.messenger.network.Broker;
import com.github.messenger.network.WebsocketConnectionPool;
import com.github.messenger.payload.Envelope;
import com.github.messenger.payload.PrivateToken;
import com.github.messenger.service.message.IGlobalMessageService;
import com.github.messenger.utils.JsonHelper;
import com.github.messenger.utils.PrivateTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.OnMessage;
import javax.websocket.Session;

public class WebsocketHandler {

    private static final Logger log = LoggerFactory.getLogger(WebsocketHandler.class);

    private final WebsocketConnectionPool websocketConnectionPool;

    private final IGlobalMessageService globalMessageService;

    private final Broker broker;

    public WebsocketHandler(
            WebsocketConnectionPool websocketConnectionPool,
            Broker broker,
            IGlobalMessageService globalMessageService) {
        this.websocketConnectionPool = websocketConnectionPool;
        this.broker = broker;
        this.globalMessageService = globalMessageService;
    }

    @OnMessage
    public void messages(Session session, String input) {
        try {
            Envelope envelope = JsonHelper.fromJson(input, Envelope.class).orElseThrow();
            PrivateToken result = PrivateTokenProvider.decode(envelope.getToken());
            if(!PrivateTokenProvider.validateToken(result)){
                throw new ExpiredToken();
            }
            switch (envelope.getTopic()) {
                case LOGIN:
                    websocketConnectionPool.addSession(result.getLogin(), session);
                    broker.broadcast(websocketConnectionPool.getSessions(), envelope);
                    break;
                case GLOBAL_MESSAGE:
                    broker.broadcast(websocketConnectionPool.getSessions(), envelope);
                    broker.send(session,envelope);
                    GlobalMessageDto dto = JsonHelper.fromJson(envelope.getPayload(), GlobalMessageDto.class).orElseThrow(IncorrectPayload::new);
                    GlobalMessage globalMessage = new GlobalMessage(
                            null,
                            result.getId(),
                            dto.getText(),
                            dto.getTime()
                    );
                    globalMessageService.save(globalMessage);
                    break;
                case LOGOUT:
                    broker.broadcast(websocketConnectionPool.getSessions(), envelope);
                    websocketConnectionPool.removeSession(result.getLogin());
                    websocketConnectionPool.getSession(result.getLogin()).close();
                    break;
            }
        } catch (Throwable e) {
            //TODO: single send to user about error
            log.warn("Enter {}", e.getMessage());
        }
    }
}
