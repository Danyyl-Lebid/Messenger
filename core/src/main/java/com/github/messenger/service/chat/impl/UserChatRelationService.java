package com.github.messenger.service.chat.impl;

import com.github.messenger.entity.UserChatRelation;
import com.github.messenger.repository.IRepository;
import com.github.messenger.service.chat.IUserChatRelationService;

public class UserChatRelationService implements IUserChatRelationService {

    private final IRepository<UserChatRelation> userChatRelationRelation;

    public UserChatRelationService(IRepository<UserChatRelation> userChatRelationRelation) {
        this.userChatRelationRelation = userChatRelationRelation;
    }

    @Override
    public void addUser(Long chatId, Long userId) {
        userChatRelationRelation.save(
                new UserChatRelation(null, userId, chatId)
        );
    }
}
