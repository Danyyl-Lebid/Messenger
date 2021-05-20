package com.github.messenger.config;

import com.github.messenger.service.user.IUserService;
import com.github.messenger.service.user.impl.UserService;


public class ServiceConfig {

    private static final IUserService userService = new UserService(RepositoryConfig.getUserRepository());

    public static IUserService getUserService() {
        return userService;
    }
}
