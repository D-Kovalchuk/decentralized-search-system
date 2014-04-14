package com.fly.house.io.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.ArrayList;
import java.util.List;

import static com.fly.house.io.event.EventType.CREATE;
import static com.fly.house.io.event.EventType.DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.util.Arrays.asList;

/**
 * Created by dimon on 2/27/14.
 */
public class EventManager {

    private Path rootPath;
    private static Logger logger = LoggerFactory.getLogger(EventManager.class);

    public EventManager(Path path) {
        rootPath = path;
    }

    public List<WatchEvent<Path>> filterEvents(List<WatchEvent<?>> events) {
        List<WatchEvent<Path>> eventList = new ArrayList<>();
        for (WatchEvent i : events) {
            WatchEvent<Path> event = cast(i);
            Path path = event.context();
            Path absolutePath = rootPath.resolve(path);
            if (absolutePath.toFile().isDirectory()) {
                logger.debug("creation directories unsupported");
                continue;
            }
            if (isHidden(absolutePath) || isTemporal(absolutePath)) {
                logger.debug("{} file is hidden or temporal", absolutePath.toString());
                continue;
            }
            eventList.add(event);
        }
        return eventList;
    }

    public List<Event> encapsulateEvents(List<WatchEvent<Path>> events) {
        if (!events.isEmpty()) {
            if (isModifyEvent(events)) {
                return createModifyEvent(events);
            }
            if (events.size() == 1) {
                WatchEvent<Path> event = events.get(0);
                if (isCreateEvent(event)) {
                    logger.debug("Create event has been fired! [path {}]", event.context());
                    Event create = createEvent(event, CREATE);
                    return asList(create);
                }
                if (isDeleteEvent(event)) {
                    logger.debug("Delete event has been fired! [path {}]", event.context());
                    Event delete = createEvent(event, DELETE);
                    return asList(delete);
                }
            }
        }
        return asList(EventBuilder.UNKNOWN_EVENT);
    }


    private List<Event> createModifyEvent(List<WatchEvent<Path>> events) {
        List<Event> eventList = new ArrayList<>();
        for (WatchEvent<Path> event : events) {
            if (event.kind() == ENTRY_CREATE) {
                Event createEvent = createEvent(event, CREATE);
                eventList.add(createEvent);
            }
            if (event.kind() == ENTRY_DELETE) {
                Event deleteEvent = createEvent(event, DELETE);
                eventList.add(deleteEvent);
            }
        }
        logger.debug("Modify event has been fired: {}", eventList);
        return eventList;
    }

    private Event createEvent(WatchEvent<Path> event, EventType type) {
        Path absolutePath = rootPath.resolve(event.context());
        return new EventBuilder()
                .type(type)
                .path(absolutePath)
                .build();
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
