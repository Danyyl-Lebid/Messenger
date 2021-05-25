package com.github.messenger.config;

import com.github.messenger.service.message.IGlobalMessageService;
import com.github.messenger.service.message.impl.GlobalMessageService;
import com.github.messenger.service.user.IUserService;
import com.github.messenger.service.user.impl.UserService;


public class ServiceConfig {

    private static final IUserService userService = new UserService(RepositoryConfig.getUserRepository());

    private static final IGlobalMessageService globalMessageService = new GlobalMessageService(RepositoryConfig.getGlobalMessageRepository());

    public static IUserService getUserService() {
        return userService;
    }

    public static IGlobalMessageService getGlobalMessageService() {
        return globalMessageService;
    }
}
