package com.fly.house.io.operations;

import com.fly.house.io.api.Command;
import com.fly.house.io.api.FileOperation;

import java.nio.file.Path;

/**
 * Created by dimon on 1/26/14.
 */
public class DeleteFileCommand implements Command {

    private FileOperation fileManager;
    private Path oldPath;

    public DeleteFileCommand(FileOperation fileManager, Path oldPath) {
        this.fileManager = fileManager;
        this.oldPath = oldPath;
    }

    @Override
    public void execute() {
        fileManager.delete(oldPath);
    }
}
