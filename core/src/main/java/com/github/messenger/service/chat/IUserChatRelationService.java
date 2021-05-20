package com.github.messenger.service.chat;

import com.github.messenger.entity.UserChatRelation;

import java.util.List;

public interface IUserChatRelationService {

    void addRelation(UserChatRelation relation);

    void removeRelation(UserChatRelation relation);

    List<Long> findAllUsersByChatId(Long chatId);

    List<Long> findAllChatsByUserId(Long userId);

}
