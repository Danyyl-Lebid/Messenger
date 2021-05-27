package com.github.messenger.payload;

import com.github.messenger.utils.DateUtils;

import java.util.Date;
import java.util.Objects;

public class PrivateToken {

    private Long userId;

    private String login;

    private Date createdAt;

    private Date expireIn;

    public PrivateToken() {
    }

    public PrivateToken(Long userId, String login, Date createdAt, Date expireIn) {
        this.userId = userId;
        this.login = login;
        this.createdAt = createdAt;
        this.expireIn = expireIn;
    }

    public PrivateToken(Long userId, String login, int lifetimeInMinutes) {
        this.userId = userId;
        this.login = login;
        this.createdAt = DateUtils.getCurrentDate();
        this.expireIn = DateUtils.addMinutes(this.createdAt, lifetimeInMinutes);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
        return Objects.equals(userId, that.userId) && Objects.equals(login, that.login) && Objects.equals(createdAt, that.createdAt) && Objects.equals(expireIn, that.expireIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, login, createdAt, expireIn);
    }

    @Override
    public String toString() {
        return "PrivateToken{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", createdAt=" + createdAt +
                ", expireIn=" + expireIn +
                '}';
    }
}
