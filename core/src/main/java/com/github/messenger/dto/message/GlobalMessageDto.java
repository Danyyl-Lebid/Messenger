package com.github.messenger.dto.message;

import java.util.Objects;

public class GlobalMessageDto {

    private String nickname;

    private String text;

    private Long time;

    public GlobalMessageDto() {
    }

    public GlobalMessageDto(String nickname, String text, Long time) {
        this.nickname = nickname;
        this.text = text;
        this.time = time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GlobalMessageDto that = (GlobalMessageDto) o;
        return Objects.equals(nickname, that.nickname) && Objects.equals(text, that.text) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, text, time);
    }

    @Override
    public String toString() {
        return "GlobalMessageDto{" +
                "nickname='" + nickname + '\'' +
                ", text='" + text + '\'' +
                ", time=" + time +
                '}';
    }
}
