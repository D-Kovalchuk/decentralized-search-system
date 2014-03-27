package com.fly.house.io;

import com.fly.house.io.exceptions.PathRepositoryException;
import com.fly.house.io.exceptions.WatchServiceRegistrationException;
import com.fly.house.io.exceptions.WatchServiceUnregistrationException;
import com.fly.house.io.repositories.api.FileRepo;
import com.fly.house.io.repositories.api.PathRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.Closeable;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;

/**
 * Class is not thread-safe.
 *
 * @author Dmitriy Kovalchuk
 */
@Service
public class WatchServiceStorage {

    private PathRepository pathManager;
    //todo consider swtichiing to an ConcurrentHashMap
    private Map<Path, WatchService> storage;
    private FileSystem fileSystem;
    private static Logger logger = LoggerFactory.getLogger(WatchServiceStorage.class);

    @Autowired
    public WatchServiceStorage(@FileRepo PathRepository pathManager) {
        fileSystem = FileSystems.getDefault();
        this.pathManager = pathManager;
        this.storage = new HashMap<>();
    }

    public void load() {
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

    public Map<Path, WatchService> asMap() {
        return new HashMap<>(storage);
    }

    /**
     * Close file system and associated with it other resources.
     */
    @PreDestroy
    public void destroy() {
        logger.debug("Closing file system");
        closeQuietly(fileSystem);
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
}
