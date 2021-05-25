package com.github.messenger.service.message;

import com.github.messenger.entity.GlobalMessage;

import java.util.Collection;

public interface IGlobalMessageService {

    GlobalMessage save(GlobalMessage message);

    Collection<GlobalMessage> getMessages();

}
