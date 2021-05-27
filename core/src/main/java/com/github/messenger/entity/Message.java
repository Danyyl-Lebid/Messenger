package com.github.messenger.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "messages", schema = "public")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "topic")
    private String topic;

    @Column(name = "text")
    private String text;

    @Column(name = "time")
    private Long time;

    public Message() {
    }

    public Message(Long id, Long userId, Long chatId, String topic, String text, Long time) {
        this.id = id;
        this.userId = userId;
        this.chatId = chatId;
        this.topic = topic;
        this.text = text;
        this.time = time;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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
        Message message = (Message) o;
        return Objects.equals(id, message.id) && Objects.equals(userId, message.userId) && Objects.equals(chatId, message.chatId) && Objects.equals(topic, message.topic) && Objects.equals(text, message.text) && Objects.equals(time, message.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, chatId, topic, text, time);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", userId=" + userId +
                ", chatId=" + chatId +
                ", topic='" + topic + '\'' +
                ", text='" + text + '\'' +
                ", time=" + time +
                '}';
    }
}
