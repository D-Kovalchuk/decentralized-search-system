package com.fly.house.io.operations;

import java.nio.file.Path;

/**
 * Created by dimon on 1/26/14.
 */
public interface FileOperation {

    void create(Path newPath);

    void update(Path newPath, Path oldPath);

    void delete(Path oldPath);


}
