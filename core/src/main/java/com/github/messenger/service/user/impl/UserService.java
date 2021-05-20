package com.github.messenger.service.user.impl;

import com.github.messenger.entity.User;
import com.github.messenger.exceptions.Conflict;
import com.github.messenger.exceptions.UpdateError;
import com.github.messenger.repository.IRepository;
import com.github.messenger.service.user.IUserService;
import org.hibernate.TransientObjectException;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.PersistenceException;
import java.util.Collection;

public class UserService implements IUserService {

    private final IRepository<User> userRepository;

    public UserService(IRepository<User> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Collection<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public User findByLogin(String login) {
        return this.userRepository.findBy("login", String.class, login);
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findBy("email", String.class, email);
    }

    @Override
    public User findByNickname(String nickname) {
        return this.userRepository.findBy("nickname", String.class, nickname);
    }

    @Override
    public User insert(User user) {
        try {
            this.userRepository.save(user);
        } catch (ConstraintViolationException e){
            throw new Conflict();
        }
        return user;
    }

    @Override
    public void update(User user) {
        try {
            this.userRepository.update(user);
        } catch (PersistenceException e){
            throw new UpdateError();
        }
    }

}
