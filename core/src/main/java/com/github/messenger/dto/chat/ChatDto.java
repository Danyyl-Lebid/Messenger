package com.github.messenger.dto.chat;

import java.util.Objects;

public class ChatDto {

    private Long id;

    private String chatName;

    public ChatDto() {
    }

    public ChatDto(Long id, String chatName) {
        this.id = id;
        this.chatName = chatName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return chatName;
    }

    public void setName(String chatName) {
        this.chatName = chatName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatDto chatDto = (ChatDto) o;
        return Objects.equals(id, chatDto.id) && Objects.equals(chatName, chatDto.chatName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatName);
    }

    @Override
    public String toString() {
        return "ChatDto{" +
                "id=" + id +
                ", chatName=" + chatName +
                '}';
    }
}
