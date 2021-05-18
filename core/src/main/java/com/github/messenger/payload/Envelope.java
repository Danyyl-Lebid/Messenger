package com.github.messenger.payload;

import java.util.Objects;

public class Envelope {

    private Topic topic;

    private String payload;

    public Envelope() {
    }

    public Envelope(Topic topic, String payload) {
        this.topic = topic;
        this.payload = payload;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Envelope envelope = (Envelope) o;
        return topic == envelope.topic && Objects.equals(payload, envelope.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic, payload);
    }

    @Override
    public String toString() {
        return "Envelope{" +
                "topic=" + topic +
                ", payload='" + payload + '\'' +
                '}';
    }
}
