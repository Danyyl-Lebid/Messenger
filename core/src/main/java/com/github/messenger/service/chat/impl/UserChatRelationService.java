package com.github.messenger.service.chat.impl;

import com.github.messenger.entity.UserChatRelation;
import com.github.messenger.repository.IRepository;
import com.github.messenger.service.chat.IUserChatRelationService;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserChatRelationService implements IUserChatRelationService {

    private final IRepository<UserChatRelation> userChatRelationRepository;

    public UserChatRelationService(IRepository<UserChatRelation> userChatRelationRelation) {
        this.userChatRelationRepository = userChatRelationRelation;
    }

    @Override
    public void addRelation(UserChatRelation relation) {
        userChatRelationRepository.save(relation);
    }

    @Override
    public void removeRelation(UserChatRelation relation) {
        Collection<UserChatRelation> usersInChat = userChatRelationRepository.findAllBy("chat_id", relation.getChatId());
        for(UserChatRelation user : usersInChat){
            if(Objects.equals(user.getUserId(), relation.getUserId())){
                userChatRelationRepository.delete(user);
                break;
            }
        }
    }

    @Override
    public List<Long> findAllUsersByChatId(Long chatId) {
        Collection<UserChatRelation> usersInChat = userChatRelationRepository.findAllBy("chat_id", chatId);
        return usersInChat.stream().map(UserChatRelation::getUserId).collect(Collectors.toList());
    }

    @Override
    public List<Long> findAllChatsByUserId(Long userId) {
        Collection<UserChatRelation> chatOfUser = userChatRelationRepository.findAllBy("user_id", userId);
        return chatOfUser.stream().map(UserChatRelation::getUserId).collect(Collectors.toList());
    }
}
