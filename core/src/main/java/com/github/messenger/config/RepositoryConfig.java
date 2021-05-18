package com.github.messenger.config;

import com.github.messenger.entity.User;
import com.github.messenger.repository.IRepository;
import com.github.messenger.repository.impl.HibernateRepository;
import com.github.messenger.utils.HibernateSessionManager;

public class RepositoryConfig {

    private static final HibernateSessionManager hibernateSessionManager = new HibernateSessionManager();

    private static final IRepository<User> userRepository = new HibernateRepository<>(User.class, hibernateSessionManager);

    public static IRepository<User> getUserRepository() {
        return userRepository;
    }
}
