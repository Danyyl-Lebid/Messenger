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
    private Long user_id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "text")
    private String text;

    @Column(name = "time")
    private Long time;

    public GlobalMessage() {
    }

    public GlobalMessage(Long id, Long userId, String nickname, String text, Long time) {
        this.id = id;
        this.user_id = userId;
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
        GlobalMessage that = (GlobalMessage) o;
        return Objects.equals(id, that.id) && Objects.equals(user_id, that.user_id) && Objects.equals(nickname, that.nickname) && Objects.equals(text, that.text) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, nickname, text, time);
    }

    @Override
    public String toString() {
        return "GlobalMessage{" +
                "id=" + id +
                ", userId=" + user_id +
                ", nickname='" + nickname + '\'' +
                ", text='" + text + '\'' +
                ", time=" + time +
                '}';
    }
}
