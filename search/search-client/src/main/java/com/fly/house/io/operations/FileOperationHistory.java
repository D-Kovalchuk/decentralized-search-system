package com.fly.house.io.operations;

import com.fly.house.io.event.Event;
import com.fly.house.io.operations.api.AbstractFileOperationHistory;
import com.fly.house.io.operations.api.Command;
import com.fly.house.io.operations.api.FileOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dimon on 1/27/14.
 */
@Component
public class FileOperationHistory extends AbstractFileOperationHistory {

    private Map<Event, Command> history = new ConcurrentHashMap<>();

    @Autowired
    public FileOperationHistory(FileOperation fileManager) {
        super(fileManager);
    }

    @Override
    public Map<Event, Command> getHistory() {
        return history;
    }

    @Override
    protected void create(Event event) {
        history.put(event, new CreateFileCommand(fileManager, event.getPath()));
    }

    @Override
    protected void delete(Event event) {
        history.put(event, new DeleteFileCommand(fileManager, event.getPath()));
    }

}
