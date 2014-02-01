package com.fly.house.io.operations;

import com.fly.house.io.Event;
import com.fly.house.io.api.AbstractFileOperationHistory;
import com.fly.house.io.api.Command;
import com.fly.house.io.api.FileOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by dimon on 1/27/14.
 */
@Component
public class FileOperationHistory extends AbstractFileOperationHistory {

    private List<Command> history = new CopyOnWriteArrayList<>();

    @Autowired
    public FileOperationHistory(FileOperation fileManager) {
        super(fileManager);
    }

    public List<Command> getHistory() {
        return history;
    }

    @Override
    protected void create(Event event) {
        history.add(new CreateFileCommand(fileManager, event.getNewPath()));
    }

    @Override
    protected void delete(Event event) {
        history.add(new DeleteFileCommand(fileManager, event.getOldPath()));
    }

    @Override
    protected void update(Event event) {
        history.add(new UpdateFileCommand(fileManager, event.getNewPath(), event.getOldPath()));
    }

}
