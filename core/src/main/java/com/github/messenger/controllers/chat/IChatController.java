package com.github.messenger.controllers.chat;

public interface IChatController {

    String getAllUsers();

    void createChat(String json);

    String getParticipants(String json);

    String getChats(Long userId);

}
