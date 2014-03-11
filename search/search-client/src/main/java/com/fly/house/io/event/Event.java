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

}
