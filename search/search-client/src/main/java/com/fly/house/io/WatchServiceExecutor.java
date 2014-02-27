package com.fly.house.io;

import com.fly.house.io.event.Event;
import com.fly.house.io.operations.OperationHistory;
import com.fly.house.io.snapshot.SnapshotBuilder;
import com.fly.house.io.snapshot.SnapshotComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.nio.file.WatchService;
import java.util.Collection;
import java.util.List;

/**
 * Class responsible for creating and starting <code>PathWatchService</code>.
 * First, this class creates and starts all <code>PathWatchServices</code>
 * which associated with paths that were added earlier.
 * After initialization registered paths it allows to create a new PathWatchService.
 * Class is not thread-safe.
 *
 * @author Dmitriy Kovalchuk
 */
@Component
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

    @PostConstruct
    public void init() {
        for (Path path : storage.getPaths()) {
            addChangesToHistory(path);
        }
        Collection<WatchService> services = storage.getWatchServices();
        for (WatchService service : services) {
            executor.submit(new PathWatchService(service, operationFactory));
        }
    }

    public void createWatchService(Path path) {
        addChangesToHistory(path);
        WatchService watchService = storage.register(path);
        executor.submit(new PathWatchService(watchService, operationFactory));
    }

    private void addChangesToHistory(Path path) {
        SnapshotBuilder builder = new SnapshotBuilder(path);
        SnapshotComparator comparator = new SnapshotComparator();
        List<Event> events = comparator.getDiff(builder.getFreshSnapshot(), builder.getSteelSnapshot());
        operationFactory.putCommands(events);
    }
}
