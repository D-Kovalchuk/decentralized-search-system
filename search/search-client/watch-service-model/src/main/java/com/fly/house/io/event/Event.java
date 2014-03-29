package com.fly.house.io.event;

import java.nio.file.Path;

/**
 * Created by dimon on 1/30/14.
 */
public class Event {

    private Path path;
    private EventType type;

    public Event(EventType type, Path path) {
        this.type = type;
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    public EventType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Event{" +
                "path=" + path +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (!path.equals(event.path)) return false;
        if (type != event.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = path.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
