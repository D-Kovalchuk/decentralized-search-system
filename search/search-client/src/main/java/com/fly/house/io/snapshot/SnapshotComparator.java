package com.fly.house.io.snapshot;

import com.fly.house.io.event.Event;
import com.fly.house.io.event.EventBuilder;
import com.fly.house.io.event.EventType;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static com.fly.house.io.snapshot.SnapshotBuilder.EMPTY_SNAPSHOT;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

/**
 * Created by dimon on 1/30/14.
 */
public class SnapshotComparator {

    private List<Path> newSnapshotFiles;
    private List<Path> oldSnapshotFiles;

    public List<Event> getDiff(Snapshot newSnapshot, Snapshot oldSnapshot) {
        if (newSnapshot == EMPTY_SNAPSHOT && oldSnapshot == EMPTY_SNAPSHOT) {
            return emptyList();
        }
        newSnapshotFiles = newSnapshot.getFiles();
        oldSnapshotFiles = oldSnapshot.getFiles();
        return Stream.concat(getCreatedFiles(), getDeletedFiles()).collect(toList());
    }

    private Stream<Event> getCreatedFiles() {
        return newSnapshotFiles.stream()
                .filter(file -> !oldSnapshotFiles.contains(file))
                .map(this::buildMapper)
                .map(b -> b.type(EventType.CREATE))
                .map(EventBuilder::build);
    }

    private Stream<Event> getDeletedFiles() {
        return oldSnapshotFiles.stream()
                .filter(file -> !newSnapshotFiles.contains(file))
                .map(this::buildMapper)
                .map(b -> b.type(EventType.DELETE))
                .map(EventBuilder::build);
    }

    private EventBuilder buildMapper(Path file) {
        return new EventBuilder().path(file);
    }

}
