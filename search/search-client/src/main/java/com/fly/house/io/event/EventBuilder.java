package com.fly.house.io.event;

import java.nio.file.Path;

/**
 * Created by dimon on 3/11/14.
 */
public class EventBuilder {

    private Path path;
    private EventType type;
    public static Event UNKNOWN_EVENT = new EventBuilder().type(EventType.UNKNOWN).build();

    //todo make private and method static
    public EventBuilder() {
    }

    public EventBuilder type(EventType type) {
        this.type = type;
        return this;
    }

    public EventBuilder path(Path path) {
        this.path = path;
        return this;
    }


    public Event build() {
        return new Event(type, path);
    }
}
