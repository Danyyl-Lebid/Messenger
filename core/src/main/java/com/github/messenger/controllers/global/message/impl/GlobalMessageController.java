package com.github.messenger.controllers.global.message.impl;

import com.github.messenger.controllers.global.message.IGlobalMessageController;
import com.github.messenger.dto.message.GlobalMessageDto;
import com.github.messenger.entity.GlobalMessage;
import com.github.messenger.exceptions.IncorrectPayload;
import com.github.messenger.network.Broker;
import com.github.messenger.network.WebsocketConnectionPool;
import com.github.messenger.service.message.IGlobalMessageService;
import com.github.messenger.utils.JsonHelper;

import javax.websocket.Session;
import java.util.Collection;
import java.util.Date;

public class GlobalMessageController implements IGlobalMessageController {

    private final IGlobalMessageService globalMessageService;

    private final WebsocketConnectionPool connectionPool;

    private final Broker broker;

    public GlobalMessageController(
            IGlobalMessageService globalMessageService,
            WebsocketConnectionPool connectionPool,
            Broker broker
    ) {
        this.globalMessageService = globalMessageService;
        this.connectionPool = connectionPool;
        this.broker = broker;
    }

    @Override
    public void sendHistory(Session session) {
        Collection<GlobalMessage> messages = globalMessageService.getMessages();
        for (GlobalMessage message : messages) {
            GlobalMessageDto dto = new GlobalMessageDto(
                    message.getNickname(),
                    message.getText(),
                    new Date(message.getTime())
            );
            String payload = JsonHelper.toJson(dto).orElseThrow();
            broker.send(session, payload);
        }
    }

    @Override
    public void broadcast(String payload) {
        broker.broadcast(connectionPool.getSessions(), payload);
    }

    @Override
    public void save(Long userId, String payload) {
        GlobalMessageDto dto = JsonHelper.fromJson(payload, GlobalMessageDto.class).orElseThrow(IncorrectPayload::new);
        GlobalMessage globalMessage = new GlobalMessage(
                null,
                userId,
                dto.getNickname(),
                dto.getText(),
                dto.getTime().getTime()
        );
        globalMessageService.save(globalMessage);
    }

}
