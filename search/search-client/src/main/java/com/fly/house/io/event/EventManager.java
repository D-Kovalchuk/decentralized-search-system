package com.fly.house.io.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;

/**
 * Created by dimon on 2/27/14.
 */
public class EventManager {

    private static Logger logger = LoggerFactory.getLogger(EventManager.class);

    public List<WatchEvent<Path>> filterEvents(List<WatchEvent<?>> events) {
        List<WatchEvent<Path>> eventList = new ArrayList<>();
        for (WatchEvent i : events) {
            WatchEvent<Path> event = cast(i);
            Path path = event.context();
            if (isHidden(path) || isTemporal(path)) {
                logger.debug("File is hidden or temporal");
                continue;
            }
            eventList.add(event);
        }
        return eventList;
    }

    public Event encapsulateEvents(List<WatchEvent<Path>> events) {
        if (!events.isEmpty()) {
            if (isModifyEvent(events)) {
                return createModifyEvent(events);
            }
            if (events.size() == 1) {
                WatchEvent<Path> event = events.get(0);
                if (isCreateEvent(event)) {
                    return createCreateEvent(events);
                }
                if (isDeleteEvent(event)) {
                    return createDeleteEvent(events);
                }
            }
        }
        return Event.create(EventType.UNKNOWN);
    }

    private Event createDeleteEvent(List<WatchEvent<Path>> events) {
        logger.debug("Delete event has been fired");
        Path path = events.get(0).context();
        return Event.create(EventType.DELETE).withOldPath(path);
    }

    private Event createCreateEvent(List<WatchEvent<Path>> events) {
        logger.debug("Create event has been fired");
        return Event.create(EventType.CREATE)
                .withNewPath(events.get(0).context());
    }

    private Event createModifyEvent(List<WatchEvent<Path>> events) {
        Event event1 = Event.create(EventType.MODIFY);
        for (WatchEvent<Path> event : events) {
            if (event.kind() == ENTRY_CREATE) {
                event1.withNewPath(event.context());
            }

            if (event.kind() == ENTRY_DELETE) {
                event1.withOldPath((event.context()));
            }
        }
        logger.debug("Modify event has been fired: new path {}, old path {}", event1.getNewPath(), event1.getOldPath());
        return event1;
    }


    private boolean isDeleteEvent(WatchEvent<Path> event) {
        return event.kind() == ENTRY_DELETE;
    }

    private boolean isCreateEvent(WatchEvent<Path> event) {
        return event.kind() == ENTRY_CREATE;
    }

    private boolean isModifyEvent(List<WatchEvent<Path>> events) {
        return events.size() == 2;
    }

    private boolean isTemporal(Path path) {
        return path.toString().endsWith("~");
    }

    private boolean isHidden(Path path) {
        File file = path.toFile();
        return file.isHidden();
    }

    @SuppressWarnings("unchecked")
    private <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }

}
