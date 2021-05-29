package com.github.messenger.dto.user;

import java.util.Objects;

public class UserStatusDto {

    private String nickname;

    private String status;

    public UserStatusDto() {
    }

    public UserStatusDto(String nickname, String status) {
        this.nickname = nickname;
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStatusDto that = (UserStatusDto) o;
        return Objects.equals(nickname, that.nickname) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, status);
    }

    @Override
    public String toString() {
        return "UserStatusDto{" +
                "nickname='" + nickname + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
