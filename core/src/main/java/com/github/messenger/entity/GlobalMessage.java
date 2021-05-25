package com.github.messenger.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "global_messages", schema = "public")
public class GlobalMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "text")
    private String text;

    @Column(name = "time")
    private Long time;

    public GlobalMessage() {
    }

    public GlobalMessage(Long id, Long userId, String text, Long time) {
        this.id = id;
        this.userId = userId;
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
        GlobalMessage that = (GlobalMessage) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(text, that.text) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, text, time);
    }

    @Override
    public String toString() {
        return "GlobalMessage{" +
                "id=" + id +
                ", userId=" + userId +
                ", text='" + text + '\'' +
                ", time=" + time +
                '}';
    }
}
