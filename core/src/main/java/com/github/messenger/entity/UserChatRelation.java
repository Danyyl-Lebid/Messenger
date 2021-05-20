package com.github.messenger.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_chat", schema = "public")
public class UserChatRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "chat_id")
    private Long chatId;

    public UserChatRelation() {
    }

    public UserChatRelation(Long id, Long userId, Long chatId) {
        this.id = id;
        this.userId = userId;
        this.chatId = chatId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        UserChatRelation userChatRelation = (UserChatRelation) o;
        return Objects.equals(id, userChatRelation.id) && Objects.equals(userId, userChatRelation.userId) && Objects.equals(chatId, userChatRelation.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, chatId);
    }

    @Override
    public String toString() {
        return "UserChat{" +
                "id=" + id +
                ", userId=" + userId +
                ", chatId=" + chatId +
                '}';
    }
}
