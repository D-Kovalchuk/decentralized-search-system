package com.fly.house.io;

import com.fly.house.io.api.PathRepository;
import com.fly.house.io.exceptions.PathRepositoryException;
import com.fly.house.io.exceptions.WatchServiceRegistrationException;
import com.fly.house.io.exceptions.WatchServiceUnregistrationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Closeable;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchService;
import java.util.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;

/**
 * Class is not thread-safe.
 *
 * @author Dmitriy Kovalchuk
 */
@Component
public class WatchServiceStorage {

    private PathRepository pathManager;
    private Map<Path, WatchService> storage;
    private FileSystem fileSystem;
    private static Logger logger = LoggerFactory.getLogger(WatchServiceStorage.class);

    @Autowired
    public WatchServiceStorage(PathRepository pathManager) {
        fileSystem = FileSystems.getDefault();
        this.pathManager = pathManager;
        this.storage = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        logger.info("Initialization {}", WatchServiceStorage.class.toString());
        List<Path> registeredPaths = pathManager.getPaths();
        for (Path path : registeredPaths) {
            try {
                put(path);
            } catch (IOException e) {
                logger.error("Cannot register watch service on path" + path.toString(), e);
            }
        }
    }

    public WatchService register(Path path) {
        try {
            pathManager.add(path);
            return put(path);
        } catch (PathRepositoryException | IOException e) {
            logger.warn("Cannot register watch service on path" + path.toString(), e);
            throw new WatchServiceRegistrationException(e);
        }
    }

    public WatchService unregister(Path path) {
        WatchService watchService = null;
        try {
            pathManager.remove(path);
            watchService = storage.remove(path);
            logger.debug("WatchService is closed: {}", watchService);
        } catch (PathRepositoryException e) {
            logger.warn("Cannot close watch service", e);
            throw new WatchServiceUnregistrationException(e);
        } finally {
            closeQuietly(watchService);
        }
        return watchService;
    }

    public List<WatchService> getWatchServices() {
        Collection<WatchService> values = storage.values();
        return new ArrayList<>(values);
    }

    void setStorage(Map<Path, WatchService> map) {
        storage = map;
    }

    private WatchService put(Path path) throws IOException {
        WatchService watchService = createNewWatchService();
        logger.debug("New watch service was created {}", watchService.toString());
        path.register(watchService, ENTRY_CREATE, ENTRY_DELETE);
        logger.debug("Registered watch service on {} path", path.toString());
        storage.put(path, watchService);
        return watchService;
    }

    private WatchService createNewWatchService() throws IOException {
        return fileSystem.newWatchService();
    }

    private void closeQuietly(Closeable watchService) {
        if (watchService != null) {
            try {
                watchService.close();
            } catch (IOException e) {
                logger.warn("Cannot close watch service", e);
            }
        }
    }

    /**
     * Close file system and associated with it other resources.
     */
    @PreDestroy
    public void destroy() {
        closeQuietly(fileSystem);
    }

    public List<Path> getPaths() {
        return new ArrayList<>(storage.keySet());
    }
}
