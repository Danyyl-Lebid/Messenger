package com.github.messenger.payload;

import com.github.messenger.entity.Role;
import com.github.messenger.utils.DateUtils;

import java.util.Date;
import java.util.Objects;

public class PrivateToken {

    private String email;

    private Date createdAt;

    private Date expireIn;

    public PrivateToken() {
    }

    public PrivateToken(String email, Date createdAt, Date expireIn) {
        this.email = email;
        this.createdAt = createdAt;
        this.expireIn = expireIn;
    }

    public PrivateToken(String email, int lifetimeInMinutes) {
        this.email = email;
        this.createdAt = DateUtils.getCurrentDate();
        this.expireIn = DateUtils.addMinutes(this.createdAt, lifetimeInMinutes);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return Objects.equals(email, that.email) && Objects.equals(createdAt, that.createdAt) && Objects.equals(expireIn, that.expireIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, createdAt, expireIn);
    }

    @Override
    public String toString() {
        return "PrivateToken{" +
                "email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", expireIn=" + expireIn +
                '}';
    }

}
