package com.fly.house.io.operations;

import com.fly.house.io.event.Event;
import com.fly.house.io.snapshot.Snapshot;
import com.fly.house.io.snapshot.SnapshotBuilder;
import com.fly.house.io.snapshot.SnapshotComparator;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

/**
 * Created by dimon on 1/31/14.
 */
public abstract class AbstractFileOperationHistory implements OperationHistory {

    protected FileOperation fileManager;

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
            case MODIFY:
                update(event);
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
        SnapshotBuilder builder = new SnapshotBuilder(path);
        SnapshotComparator comparator = new SnapshotComparator();
        Snapshot freshSnapshot = builder.getFreshSnapshot();
        Snapshot steelSnapshot = builder.getSteelSnapshot();
        List<Event> events = comparator.getDiff(freshSnapshot, steelSnapshot);
        putCommands(events);
    }

    protected abstract void create(Event event);

    protected abstract void delete(Event event);

    protected abstract void update(Event event);


}
