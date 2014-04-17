package com.fly.house.io.snapshot;

import com.fly.house.io.event.Event;
import com.fly.house.io.exceptions.SnapshotIOException;
import com.fly.house.io.operations.api.OperationHistory;
import com.fly.house.io.repositories.api.PathRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by dimon on 4/11/14.
 */
@Service
public class SnapshotServiceFacade {

    @Autowired
    private PathRepository pathRepository;

    @Autowired
    private OperationHistory history;

    private SnapshotComparator comparator;

    private static Logger logger = LoggerFactory.getLogger(SnapshotServiceFacade.class);


    public SnapshotServiceFacade() {
        comparator = new SnapshotComparator();
    }

    public List<Event> getDiff(Path path) {
        SnapshotBuilder builder = new SnapshotBuilder(path);
        Snapshot freshSnapshot = builder.getFreshSnapshot();
        Snapshot steelSnapshot = builder.getStaleSnapshot();
        return comparator.getDiff(freshSnapshot, steelSnapshot);
    }

    @PreDestroy
    public void destroy() {
        if (!history.getHistory().isEmpty()) {
            return;
        }
        List<Path> paths = pathRepository.getPaths();
        paths.forEach(k -> {
            try {
                SnapshotBuilder builder = new SnapshotBuilder(k);
                Snapshot snapshot = builder.getFreshSnapshot();
                builder.save(snapshot);
            } catch (SnapshotIOException e) {
                logger.warn("while exiting the system occur exception:", e);
            }
        });
    }

}
