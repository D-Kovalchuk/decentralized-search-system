package com.fly.house.io;

import com.fly.house.io.operations.api.OperationHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.WatchService;
import java.util.Map;

/**
 * Class responsible for creating and starting <code>PathWatchService</code>.
 * First, this class creates and starts all <code>PathWatchServices</code>
 * which associated with paths that were added earlier.
 * After initialization registered paths it allows to create a new PathWatchService.
 * Class is not thread-safe.
 *
 * @author Dmitriy Kovalchuk
 */
@Service
public class WatchServiceExecutor {

    private final OperationHistory operationFactory;
    private final WatchServiceStorage storage;
    private final ThreadPoolTaskExecutor executor;

    @Autowired
    public WatchServiceExecutor(WatchServiceStorage storage,
                                ThreadPoolTaskExecutor executor,
                                OperationHistory operationFactory) {
        this.storage = storage;
        this.operationFactory = operationFactory;
        this.executor = executor;
    }

    public void init() {
        for (Map.Entry<Path, WatchService> entry : storage.asMap().entrySet()) {
            operationFactory.addChangesToHistory(entry.getKey());
            executor.submit(new PathWatchService(entry.getValue(), operationFactory));
        }
    }

    public void createWatchService(Path path) {
        operationFactory.addChangesToHistory(path);
        WatchService watchService = storage.register(path);
        executor.submit(new PathWatchService(watchService, operationFactory));
    }


}
