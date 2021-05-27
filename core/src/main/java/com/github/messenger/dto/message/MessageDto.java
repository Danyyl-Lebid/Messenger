package com.github.messenger.dto.message;

import java.util.Objects;

public class MessageDto {

    private Long chatId;

    private String text;

    private Long time;

    public MessageDto() {
    }

    public MessageDto(Long chatId, String text, Long time) {
        this.chatId = chatId;
        this.text = text;
        this.time = time;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
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
        MessageDto that = (MessageDto) o;
        return Objects.equals(chatId, that.chatId) && Objects.equals(text, that.text) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, text, time);
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "chatId=" + chatId +
                ", text='" + text + '\'' +
                ", time=" + time +
                '}';
    }
}
