package com.fly.house.io;

import com.fly.house.authentication.aspect.Secure;
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
import static java.util.Collections.unmodifiableMap;

/**
 * Class is not thread-safe.
 *
 * @author Dmitriy Kovalchuk
 */
@Service
public class InMemoryWSStorage implements WatchServiceStorage {

    private PathRepository pathManager;
    private Map<Path, WatchService> storage;
    private FileSystem fileSystem;
    private static Logger logger = LoggerFactory.getLogger(InMemoryWSStorage.class);

    @Autowired
    public InMemoryWSStorage(@FileRepo PathRepository pathManager) {
        fileSystem = FileSystems.getDefault();
        this.pathManager = pathManager;
        this.storage = new HashMap<>();
    }

    public void load() {
        logger.info("Initialization {}", InMemoryWSStorage.class.toString());
        List<Path> registeredPaths = pathManager.getPaths();
        for (Path path : registeredPaths) {
            try {
                put(path);
            } catch (IOException e) {
                logger.error("Cannot register watch service on path" + path.toString(), e);
            }
        }
    }

    @Secure
    @Override
    public WatchService register(Path path) {
        try {
            pathManager.add(path);
            return put(path);
        } catch (PathRepositoryException | IOException e) {
            logger.warn("Cannot register watch service on path" + path.toString(), e);
            throw new WatchServiceRegistrationException(e);
        }
    }

    @Secure
    @Override
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

    @Secure
    @Override
    public Map<Path, WatchService> asMap() {
        return unmodifiableMap(storage);
    }

    /**
     * Close file system and associated with it other resources.
     */
    @Override
    @PreDestroy
    public void destroy() {
        logger.debug("Closing file system");
        closeQuietly(fileSystem);
    }

    @Override
    public void cleanUp() {
        storage.forEach((k, v) -> {
            try {
                v.close();
                logger.debug("closing watch service on {} path", k);
            } catch (IOException e) {
                logger.warn("fail cleaning resources", e);
            }
        });
        logger.debug("Purging storage");
        storage.clear();
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
            } catch (IOException | UnsupportedOperationException e) {
                logger.warn("Cannot close watch service", e);
            }
        }
    }
}
