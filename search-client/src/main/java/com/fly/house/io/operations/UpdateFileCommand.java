package com.fly.house.io.operations;

import com.fly.house.io.api.Command;
import com.fly.house.io.api.FileOperation;

import java.nio.file.Path;

/**
 * Created by dimon on 1/26/14.
 */
public class UpdateFileCommand implements Command {

    private FileOperation fileManager;
    private Path newPath;
    private Path oldPath;

    public UpdateFileCommand(FileOperation fileManager, Path newPath, Path oldPath) {
        this.fileManager = fileManager;
    }

    @Override
    public void execute() {
        fileManager.update(newPath, oldPath);
    }

}
