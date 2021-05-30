package com.github.messenger.dto.chat;

import java.util.Objects;

public class ChatDto {

    private Long id;

    private String name;

    public ChatDto() {
    }

    public ChatDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatDto chatDto = (ChatDto) o;
        return Objects.equals(id, chatDto.id) && Objects.equals(name, chatDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "ChatDto{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
