package com.fly.house.io.repositories.api;

import com.fly.house.io.exceptions.DirectoryNotFoundException;
import com.fly.house.io.exceptions.NotDirectoryException;
import com.fly.house.io.exceptions.PathRegisteredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

/**
 * Created by dimon on 2/27/14.
 */
public abstract class AbstractPathRepository implements PathRepository {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void add(Path path) {
        isDirectory(path);
        isRealPath(path);
        isPathRegistered(path);
    }

    @Override
    public void remove(Path path) {
        isDirectory(path);
        isRealPath(path);
    }

    private void isRealPath(final Path path) {
        if (!path.toAbsolutePath().toFile().exists()) {
            logger.debug("{} is not real path", path.toString());
            throw new DirectoryNotFoundException();
        }
    }

    private void isDirectory(final Path path) {
        if (path.toFile().isFile()) {
            logger.debug("{} is not directory", path.toString());
            throw new NotDirectoryException(path.toString() + " is not directory");
        }
    }

    private void isPathRegistered(final Path path) {
        if (getPaths().contains(path)) {
            logger.debug("Path {} is already registered", path);
            throw new PathRegisteredException("Path " + path.toString() + " is already registered");
        }
    }


}
