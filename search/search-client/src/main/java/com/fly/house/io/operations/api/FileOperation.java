package com.fly.house.io.operations.api;

import java.nio.file.Path;

/**
 * Created by dimon on 1/26/14.
 */
public interface FileOperation {

    void create(Path path);

    void delete(Path path);


}
