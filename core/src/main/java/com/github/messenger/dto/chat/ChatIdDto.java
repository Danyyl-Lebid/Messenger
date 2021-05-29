package com.github.messenger.dto.chat;

import java.util.Objects;

public class ChatIdDto {

    private Long id;

    public ChatIdDto() {
    }

    public ChatIdDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatIdDto chatIdDto = (ChatIdDto) o;
        return Objects.equals(id, chatIdDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ChatIdDto{" +
                "id=" + id +
                '}';
    }
}
