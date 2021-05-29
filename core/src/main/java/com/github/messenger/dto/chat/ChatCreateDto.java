package com.github.messenger.dto.chat;

import java.util.Arrays;
import java.util.Objects;

public class ChatCreateDto {

    private String name;

    private String[] nicknames;

    public ChatCreateDto() {
    }

    public ChatCreateDto(String name, String[] nicknames) {
        this.name = name;
        this.nicknames = nicknames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getNicknames() {
        return nicknames;
    }

    public void setNicknames(String[] nicknames) {
        this.nicknames = nicknames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatCreateDto that = (ChatCreateDto) o;
        return Objects.equals(name, that.name) && Arrays.equals(nicknames, that.nicknames);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(nicknames);
        return result;
    }

    @Override
    public String toString() {
        return "ChatCreateDto{" +
                "name='" + name + '\'' +
                ", nicknames=" + Arrays.toString(nicknames) +
                '}';
    }
}
