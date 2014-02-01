package com.fly.house.io.api;

import java.nio.file.Path;
import java.util.List;

/**
 * Created by dimon on 1/28/14.
 */
public interface PathRepository {

    void add(Path path);

    void remove(Path path);

    List<Path> getPaths();

}
