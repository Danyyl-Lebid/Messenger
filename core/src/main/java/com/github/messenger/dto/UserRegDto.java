package com.github.messenger.dto;

import com.github.messenger.payload.Role;
import com.github.messenger.entity.User;
import com.github.messenger.payload.Status;

import java.util.Objects;

public class UserRegDto {

    private String firstName;

    private String lastName;

    private String login;

    private String password;

    private String passwordConfirm;

    private String email;

    private String nickname;

    private String phone;

    public UserRegDto() {
    }

    public UserRegDto(
            String firstName,
            String lastName,
            String login,
            String password,
            String passwordConfirm,
            String email,
            String nickname,
            String phone
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.email = email;
        this.nickname = nickname;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegDto that = (UserRegDto) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(login, that.login) && Objects.equals(password, that.password) && Objects.equals(passwordConfirm, that.passwordConfirm) && Objects.equals(email, that.email) && Objects.equals(nickname, that.nickname) && Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, login, password, passwordConfirm, email, nickname, phone);
    }

    @Override
    public String toString() {
        return "UserRegDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public User toUser(){
        return new User(
                null,
                this.firstName,
                this.lastName,
                this.login,
                this.password,
                this.email,
                this.nickname,
                this.phone,
                Role.USER,
                Status.OFFLINE,
                0L
        );
    }

}
