package com.github.messenger.dto;

import com.github.messenger.entity.Role;
import com.github.messenger.entity.User;

import java.util.Objects;

public class UserAuthDto {

    private String login;

    private String password;

    public UserAuthDto() {
    }

    public UserAuthDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAuthDto that = (UserAuthDto) o;
        return Objects.equals(login, that.login) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }

    @Override
    public String toString() {
        return "UserAuthDto{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public User toUser(){
        return new User(
                null,
                null,
                null,
                this.login,
                this.password,
                null,
                null,
                null,
                Role.USER
        );
    }

}
