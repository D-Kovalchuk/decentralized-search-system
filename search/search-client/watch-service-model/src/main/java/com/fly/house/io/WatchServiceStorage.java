package com.fly.house.io;

import java.nio.file.Path;
import java.nio.file.WatchService;
import java.util.Map;

/**
 * Created by dimon on 4/7/14.
 */
public interface WatchServiceStorage {

    WatchService register(Path path);

    WatchService unregister(Path path);

    void cleanUp();

    void destroy();

    Map<Path, WatchService> asMap();
}
