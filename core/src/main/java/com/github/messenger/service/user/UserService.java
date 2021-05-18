package com.github.messenger.service.user;

import com.github.messenger.entity.User;
import com.github.messenger.repository.IRepository;

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
        return this.userRepository.findBy("id", id);
    }

    @Override
    public User findByLogin(String login) {
        return this.userRepository.findBy("login", login);
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findBy("email", email);
    }

    @Override
    public User findByNickname(String nickname) {
        return this.userRepository.findBy("nickname", nickname);
    }

    @Override
    public User insert(User user) {
        this.userRepository.save(user);
        return this.userRepository.findById(user.getId());
    }

    @Override
    public void update(User user) {
        this.userRepository.update(user);
    }

}
