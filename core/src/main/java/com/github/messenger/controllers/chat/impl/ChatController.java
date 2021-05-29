package com.github.messenger.controllers.chat.impl;

import com.github.messenger.controllers.chat.IChatController;
import com.github.messenger.dto.chat.ChatCreateDto;
import com.github.messenger.dto.chat.ChatIdDto;
import com.github.messenger.dto.user.UserStatusDto;
import com.github.messenger.entity.Chat;
import com.github.messenger.entity.User;
import com.github.messenger.entity.UserChatRelation;
import com.github.messenger.service.chat.IChatService;
import com.github.messenger.service.chat.IUserChatRelationService;
import com.github.messenger.service.user.IUserService;
import com.github.messenger.utils.JsonHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ChatController implements IChatController {

    private final IUserService userService;

    private final IChatService chatService;

    private final IUserChatRelationService userChatRelationService;

    public ChatController(IUserService userService, IChatService chatService, IUserChatRelationService userChatRelationService) {
        this.userService = userService;
        this.chatService = chatService;
        this.userChatRelationService = userChatRelationService;
    }

    @Override
    public String getAllUsers() {
        Collection<UserStatusDto> resultList = userService
                .findAll().stream()
                .map(u -> new UserStatusDto(
                        u.getNickname(),
                        u.getStatus().toString()))
                .collect(Collectors.toList());
        return JsonHelper.toJson(resultList).orElseThrow();
    }

    @Override
    public void createChat(String json) {
        ChatCreateDto dto = JsonHelper.fromJson(json, ChatCreateDto.class).orElseThrow();
        Chat chat = new Chat(null, dto.getName());
        chatService.create(new Chat(null, dto.getName()));
        for (String nickname : dto.getNicknames()) {
            User user = userService.findByNickname(nickname);
            userChatRelationService.addRelation(new UserChatRelation(null, user.getId(), chat.getId()));
        }
    }

    @Override
    public String getParticipants(String json) {
        ChatIdDto dto = JsonHelper.fromJson(json, ChatIdDto.class).orElseThrow();
        Collection<Long> userIds = userChatRelationService.findAllUsersByChatId(dto.getId());
        Collection<User> participants = new ArrayList<>();
        for (Long id : userIds) {
            participants.add(userService.findById(id));
        }
        Collection<UserStatusDto> resultList = participants.stream()
                .map(u -> new UserStatusDto(
                        u.getNickname(),
                        u.getStatus().toString()))
                .collect(Collectors.toList());
        return JsonHelper.toJson(resultList).orElseThrow();
    }
}
