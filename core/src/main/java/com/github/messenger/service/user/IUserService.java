package com.github.messenger.service.user;

import com.github.messenger.entity.User;

import java.util.Collection;

public interface IUserService {

    Collection<User> findAll();

    User findById(Long id);

    User findByLogin(String login);

    User findByEmail(String email);

    User findByNickname(String nickname);

    void insert(User user);

    void update(User user);

}
