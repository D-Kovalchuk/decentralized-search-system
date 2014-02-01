package com.fly.house.io.operations;

import com.fly.house.io.api.Command;
import com.fly.house.io.api.FileOperation;

import java.nio.file.Path;

/**
 * Created by dimon on 1/26/14.
 */
public class CreateFileCommand implements Command {

    private FileOperation fileManager;
    private Path newPath;

    public CreateFileCommand(FileOperation fileManager, Path newPath) {
        this.fileManager = fileManager;
        this.newPath = newPath;
    }

    @Override
    public void execute() {
        fileManager.create(newPath);
    }
}
