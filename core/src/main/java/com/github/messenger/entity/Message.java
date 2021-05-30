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
    private Long user_id;

    @Column(name = "chat_id")
    private Long chat_id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "text")
    private String text;

    @Column(name = "time")
    private Long time;

    public Message() {
    }

    public Message(Long id, Long userId, Long chatId, String nickname, String text, Long time) {
        this.id = id;
        this.user_id = userId;
        this.chat_id = chatId;
        this.nickname = nickname;
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
        return user_id;
    }

    public void setUserId(Long userId) {
        this.user_id = userId;
    }

    public Long getChatId() {
        return chat_id;
    }

    public void setChatId(Long chatId) {
        this.chat_id = chatId;
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
        return Objects.equals(id, message.id) && Objects.equals(user_id, message.user_id) && Objects.equals(chat_id, message.chat_id) && Objects.equals(nickname, message.nickname) && Objects.equals(text, message.text) && Objects.equals(time, message.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, chat_id, nickname, text, time);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", userId=" + user_id +
                ", chatId=" + chat_id +
                ", nickname=" + nickname +
                ", text='" + text + '\'' +
                ", time=" + time +
                '}';
    }
}
