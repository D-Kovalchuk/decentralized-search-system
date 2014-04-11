package com.fly.house.io.operations.api;

import com.fly.house.io.event.Event;
import com.fly.house.io.snapshot.SnapshotServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

/**
 * Created by dimon on 1/31/14.
 */
public abstract class AbstractFileOperationHistory implements OperationHistory {

    protected FileOperation fileManager;

    @Autowired
    private SnapshotServiceFacade snapshotService;

    private static Logger logger = LoggerFactory.getLogger(AbstractFileOperationHistory.class);

    public AbstractFileOperationHistory(FileOperation fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void putCommand(Event event) {
        switch (event.getType()) {
            case CREATE:
                create(event);
                break;
            case DELETE:
                delete(event);
                break;
        }
    }

    @Override
    public void putCommands(Collection<? extends Event> events) {
        for (Event event : events) {
            putCommand(event);
        }
    }

    @Override
    public void addChangesToHistory(Path path) {
        List<Event> events = snapshotService.getDiff(path);
        logger.debug("in {} has been made changes: {}", path, events);
        putCommands(events);
    }

    protected abstract void create(Event event);

    protected abstract void delete(Event event);


}
