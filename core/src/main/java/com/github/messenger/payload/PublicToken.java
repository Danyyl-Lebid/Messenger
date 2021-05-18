package com.github.messenger.payload;

import com.github.messenger.entity.Role;

import java.util.Objects;

public class PublicToken {

    private Role role;

    private String nickname;

    public PublicToken() {
    }

    public PublicToken(Role role, String nickname) {
        this.role = role;
        this.nickname = nickname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublicToken that = (PublicToken) o;
        return role == that.role && Objects.equals(nickname, that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, nickname);
    }

    @Override
    public String toString() {
        return "PublicToken{" +
                "role=" + role +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
