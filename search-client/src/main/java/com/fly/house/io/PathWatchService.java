package com.fly.house.io;

import com.fly.house.io.operations.FileOperationHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.fly.house.io.EventType.*;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;

/**
 * Created by dimon on 1/26/14.
 */
public class PathWatchService implements Runnable {

    private volatile boolean stop = false;
    private FileOperationHistory operationFactory;
    private volatile WatchService watchService;
    private static Logger logger = LoggerFactory.getLogger(PathWatchService.class);

    public PathWatchService(WatchService watchService, FileOperationHistory operationHistory) {
        this.watchService = watchService;
        this.operationFactory = operationHistory;
    }

    @Override
    public void run() {
        while (!isStop()) {
            startWatch();
        }
    }

    //TODO write more tests
    void startWatch() {
        try {
            logger.debug("Block on obtaining WatchKey");
            WatchKey watchKey = watchService.poll(5, TimeUnit.MINUTES);
            logger.debug("WatchKey is taken: {}", watchKey);
            if (watchKey != null) {
                List<WatchEvent<?>> events = watchKey.pollEvents();
                List<WatchEvent<Path>> contexts = filterEvents(events);
                Event event = encapsulateEvents(contexts);
                operationFactory.putCommand(event);
                watchKey.reset();
                checkWatchKey(watchKey);
                logger.debug("WatchKey has been rested: {}", watchKey);
            }
        } catch (InterruptedException | ClosedWatchServiceException e) {
            stop();
            logger.warn("WatchService has been interrupted:", e);
        }
    }

    public void stop() {
        stop = true;
    }

    public boolean isStop() {
        return stop;
    }


    Event encapsulateEvents(List<WatchEvent<Path>> events) {
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
        return new Event(UNKNOWN);
    }

    private Event createDeleteEvent(List<WatchEvent<Path>> events) {
        logger.debug("Delete event has been fired");
        Path path = events.get(0).context();
        return Event.create(DELETE).withOldPath(path);
    }

    private Event createCreateEvent(List<WatchEvent<Path>> events) {
        logger.debug("Create event has been fired");
        return Event.create(CREATE)
                .withNewPath(events.get(0).context());
    }

    private Event createModifyEvent(List<WatchEvent<Path>> events) {
        Event event1 = new Event(MODIFY);
        for (WatchEvent<Path> event : events) {
            if (event.kind() == ENTRY_CREATE) {
                event1.setNewPath(event.context());
            }

            if (event.kind() == ENTRY_DELETE) {
                event1.setOldPath((event.context()));
            }
        }
        logger.debug("Modify event has been fired: new path {}, old path {}", event1.getNewPath(), event1.getOldPath());
        return event1;
    }

    private List<WatchEvent<Path>> filterEvents(List<WatchEvent<?>> events) {
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

    private void checkWatchKey(WatchKey key) {
        if (!key.isValid()) {
            logger.debug("WatchKey is invalid: {}", key);
            stop();
        }
    }

    @SuppressWarnings("unchecked")
    private <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }


}
