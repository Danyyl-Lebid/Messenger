package com.github.messenger.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "last_messages", schema = "public")
public class LastMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "message_id")
    private Long messageId;

    @Column(name = "chat_id")
    private Long chatId;

    public LastMessage() {
    }

    public LastMessage(Long id, Long messageId, Long chatId) {
        this.id = id;
        this.messageId = messageId;
        this.chatId = chatId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
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
        LastMessage that = (LastMessage) o;
        return Objects.equals(id, that.id) && Objects.equals(messageId, that.messageId) && Objects.equals(chatId, that.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, messageId, chatId);
    }

    @Override
    public String toString() {
        return "LastMessage{" +
                "id=" + id +
                ", messageId=" + messageId +
                ", chatId=" + chatId +
                '}';
    }
}
