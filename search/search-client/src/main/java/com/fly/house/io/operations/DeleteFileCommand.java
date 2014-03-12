package com.fly.house.io.operations;

import java.nio.file.Path;

/**
 * Created by dimon on 1/26/14.
 */
public class DeleteFileCommand implements Command {

    private FileOperation fileManager;
    private Path path;

    public DeleteFileCommand(FileOperation fileManager, Path oldPath) {
        this.fileManager = fileManager;
        this.path = oldPath;
    }

    @Override
    public void execute() {
        fileManager.delete(path);
    }
}
