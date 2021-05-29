package com.github.messenger.dto;

import java.util.Objects;

public class HistoryRequestDto {

    private Long chatId;

    public HistoryRequestDto() {
    }

    public HistoryRequestDto(Long chatId) {
        this.chatId = chatId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryRequestDto that = (HistoryRequestDto) o;
        return Objects.equals(chatId, that.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId);
    }

    @Override
    public String toString() {
        return "HistoryRequestDto{" +
                "chatId=" + chatId +
                '}';
    }
}
