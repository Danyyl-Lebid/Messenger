package com.github.messenger.payload;

import com.github.messenger.utils.DateUtils;

import java.util.Date;
import java.util.Objects;

public class PrivateToken {

    private Long id;

    private String login;

    private Date createdAt;

    private Date expireIn;

    public PrivateToken() {
    }

    public PrivateToken(Long id, String login, Date createdAt, Date expireIn) {
        this.id = id;
        this.login = login;
        this.createdAt = createdAt;
        this.expireIn = expireIn;
    }

    public PrivateToken(Long id, String login, int lifetimeInMinutes) {
        this.id = id;
        this.login = login;
        this.createdAt = DateUtils.getCurrentDate();
        this.expireIn = DateUtils.addMinutes(this.createdAt, lifetimeInMinutes);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return Objects.equals(id, that.id) && Objects.equals(login, that.login) && Objects.equals(createdAt, that.createdAt) && Objects.equals(expireIn, that.expireIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, createdAt, expireIn);
    }

    @Override
    public String toString() {
        return "PrivateToken{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", createdAt=" + createdAt +
                ", expireIn=" + expireIn +
                '}';
    }
}
