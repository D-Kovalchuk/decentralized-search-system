package com.fly.house.io.operations;

import com.fly.house.io.event.Event;

import java.util.Collection;

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

    protected abstract void create(Event event);

    protected abstract void delete(Event event);

    protected abstract void update(Event event);


}
