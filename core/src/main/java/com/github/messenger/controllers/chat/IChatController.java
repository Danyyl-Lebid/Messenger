package com.github.messenger.controllers.chat;

import java.util.Collection;

public interface IChatController {

    String getAllUsers();

    void createChat(Collection<String> nicknames, String chatName);

    String getParticipants(Long chatId);

}
