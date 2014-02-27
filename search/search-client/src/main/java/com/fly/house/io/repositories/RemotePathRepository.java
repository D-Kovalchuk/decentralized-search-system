package com.fly.house.io.repositories;

import java.nio.file.Path;
import java.util.List;

/**
 * Created by dimon on 1/29/14.
 */
public class RemotePathRepository extends AbstractPathRepository {

    @Override
    public void add(Path path) {
        super.add(path);
    }

    @Override
    public void remove(Path path) {
        super.remove(path);
    }

    @Override
    public List<Path> getPaths() {
        return null;
    }
}
