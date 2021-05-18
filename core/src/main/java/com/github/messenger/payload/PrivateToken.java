package com.github.messenger.payload;

import com.github.messenger.entity.Role;

import java.util.Date;
import java.util.Objects;

public class PrivateToken {

    private String login;

    private Role role;

    private Date createdAt;

    private Date expireIn;

    public PrivateToken() {
    }

    public PrivateToken(String login, Role role, Date createdAt, Date expireIn) {
        this.login = login;
        this.role = role;
        this.createdAt = createdAt;
        this.expireIn = expireIn;
    }

    public PrivateToken(String login, Role role) {
        this.login = login;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(Date expireIn) {
        this.expireIn = expireIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrivateToken that = (PrivateToken) o;
        return Objects.equals(login, that.login) && role == that.role && Objects.equals(createdAt, that.createdAt) && Objects.equals(expireIn, that.expireIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, role, createdAt, expireIn);
    }

    @Override
    public String toString() {
        return "PrivateToken{" +
                "login='" + login + '\'' +
                ", role=" + role +
                ", createdAt=" + createdAt +
                ", expireIn=" + expireIn +
                '}';
    }
}
