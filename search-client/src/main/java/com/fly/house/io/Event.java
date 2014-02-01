package com.fly.house.io;

import java.nio.file.Path;

import static com.fly.house.io.EventType.CREATE;
import static com.fly.house.io.EventType.DELETE;

/**
 * Created by dimon on 1/30/14.
 */
public class Event {

    private Path newPath;
    private Path oldPath;

    private static Event instance;
    private EventType type;

    public Event(EventType type, Path newPath, Path oldPath) {
        this.newPath = newPath;
        this.oldPath = oldPath;
        this.type = type;
    }

    public Event(EventType type, Path newPath) {
        this.newPath = newPath;
        this.type = type;
    }

    public Event(EventType type) {
        this.type = type;
    }

    public void setNewPath(Path newPath) {
        this.newPath = newPath;
    }

    public void setOldPath(Path oldPath) {
        this.oldPath = oldPath;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Path getNewPath() {
        return newPath;
    }

    public Path getOldPath() {
        return oldPath;
    }

    public EventType getType() {
        return type;
    }

    public Event withNewPath(Path path) {
        if (instance.type == DELETE) {
            throw new IllegalStateException(String.format("Cannot call this method on %s event", DELETE));
        }
        newPath = path;
        return instance;
    }

    public Event withOldPath(Path path) {
        if (instance.type == CREATE) {
            throw new IllegalStateException(String.format("Cannot call this method on %s event", CREATE));
        }
        oldPath = path;
        return instance;
    }

    public static Event create(EventType type) {
        instance = new Event(type);
        return instance;
    }


    public Event and() {
        return instance;
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
