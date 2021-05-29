package com.github.messenger.dto.message;

import java.util.Date;
import java.util.Objects;

public class MessageDto {

    private Long chatId;

    private String nickname;

    private String text;

    private Date time;

    public MessageDto() {
    }

    public MessageDto(Long chatId, String nickname, String text, Date time) {
        this.chatId = chatId;
        this.nickname = nickname;
        this.text = text;
        this.time = time;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDto dto = (MessageDto) o;
        return Objects.equals(chatId, dto.chatId) && Objects.equals(nickname, dto.nickname) && Objects.equals(text, dto.text) && Objects.equals(time, dto.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, nickname, text, time);
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "chatId=" + chatId +
                ", nickname='" + nickname + '\'' +
                ", text='" + text + '\'' +
                ", time=" + time +
                '}';
    }
}
