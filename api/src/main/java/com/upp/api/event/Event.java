package com.upp.api.event;

import java.time.LocalDateTime;

public class Event<K, T> {

    public enum Type {
        CREATE,
        DELETE
    }

    private Event.Type eventType;
    private K key;
    private T data;
    private LocalDateTime eventCreatedAt;

    public Event() {
        eventType = null;
        key = null;
        data = null;
        eventCreatedAt = LocalDateTime.now();
    }

    public Event(Type eventType, K key, T data) {
        this.eventType = eventType;
        this.key = key;
        this.data = data;
    }

    public Type getEventType() {
        return eventType;
    }

    public K getKey() {
        return key;
    }

    public T getData() {
        return data;
    }

    public LocalDateTime getEventCreatedAt() {
        return eventCreatedAt;
    }
}
