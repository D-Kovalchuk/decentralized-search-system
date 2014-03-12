package com.fly.house.io.operations;

import java.nio.file.Path;

/**
 * Created by dimon on 1/26/14.
 */
public interface FileOperation {

    void create(Path path);

    void update(Path path);

    void delete(Path path);


}
