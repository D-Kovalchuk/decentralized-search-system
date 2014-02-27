package com.fly.house.io.event;

import java.nio.file.Path;

/**
 * Created by dimon on 1/30/14.
 */
public class Event {

    private Path newPath;
    private Path oldPath;
    private EventType type;

    private Event() {
    }

    private Event(EventType type) {
        this.type = type;
    }

    public Event withNewPath(Path path) {
        if (type == EventType.DELETE || type == EventType.UNKNOWN) {
            throw new UnsupportedOperationException("Operation unsupported for DELETE and UNKNOWN events");
        }
        newPath = path;
        return this;
    }

    public Event withOldPath(Path path) {
        if (type == EventType.CREATE || type == EventType.UNKNOWN) {
            throw new UnsupportedOperationException("Operation unsupported for CREATE and UNKNOWN events");
        }
        oldPath = path;
        return this;
    }

    public static Event create(EventType type) {
        return new Event(type);
    }

    public Path getNewPath() {
        if (type == EventType.DELETE || type == EventType.UNKNOWN) {
            throw new UnsupportedOperationException("Operation unsupported for DELETE  and UNKNOWN events");
        }
        return newPath;
    }

    public Path getOldPath() {
        if (type == EventType.CREATE || type == EventType.UNKNOWN) {
            throw new UnsupportedOperationException("Operation unsupported for CREATE and UNKNOWN events");
        }
        return oldPath;
    }

    public EventType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (newPath != null ? !newPath.equals(event.newPath) : event.newPath != null) return false;
        if (oldPath != null ? !oldPath.equals(event.oldPath) : event.oldPath != null) return false;
        if (type != event.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = newPath != null ? newPath.hashCode() : 0;
        result = 31 * result + (oldPath != null ? oldPath.hashCode() : 0);
        result = 31 * result + type.hashCode();
        return result;
    }
}
