package com.fly.house.io.operations;

import java.nio.file.Path;

/**
 * Created by dimon on 1/26/14.
 */
public class CreateFileCommand implements Command {

    private FileOperation fileManager;
    private Path path;

    public CreateFileCommand(FileOperation fileManager, Path newPath) {
        this.fileManager = fileManager;
        this.path = newPath;
    }

    @Override
    public void execute() {
        fileManager.create(path);
    }
}
