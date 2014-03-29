package com.fly.house.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

/**
 * Created by dimon on 3/29/14.
 */
@Service
public class PathAccessManager {

    @Autowired
    private WatchServiceStorage storage;

    public boolean getPath(Path path) {
        return storage.asMap().containsKey(path);
    }


}
