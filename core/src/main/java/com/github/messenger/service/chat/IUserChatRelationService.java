package com.github.messenger.service.chat;

import com.github.messenger.entity.UserChatRelation;

public interface IUserChatRelationService {

    void addRelation(UserChatRelation relation);

    void removeRelation(UserChatRelation relation);

}
