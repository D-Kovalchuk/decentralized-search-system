package com.fly.house.io.snapshot;

import com.fly.house.io.event.Event;
import com.fly.house.io.event.EventBuilder;
import com.fly.house.io.event.EventType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.fly.house.io.snapshot.SnapshotBuilder.EMPTY_SNAPSHOT;
import static java.util.Collections.emptyList;

/**
 * Created by dimon on 1/30/14.
 */
public class SnapshotComparator {

    private List<Event> list;

    public List<Event> getDiff(Snapshot newSnapshot, Snapshot oldSnapshot) {
        if (newSnapshot == EMPTY_SNAPSHOT && oldSnapshot == EMPTY_SNAPSHOT) {
            return emptyList();
        }
        list = new ArrayList<>();
        List<File> newSnapshotFiles = newSnapshot.getFiles();
        List<File> oldSnapshotFiles = oldSnapshot.getFiles();
        fillWithCreatedFiles(newSnapshotFiles, oldSnapshotFiles);
        fillWithDeletedFiles(newSnapshotFiles, oldSnapshotFiles);
        return list;
    }

    private void fillWithDeletedFiles(List<File> newSnapshotFiles, List<File> oldSnapshotFiles) {
        List<File> removed = getDeletedFiles(newSnapshotFiles, oldSnapshotFiles);
        for (File file : removed) {
            Event event = new EventBuilder().type(EventType.DELETE)
                    .path(file.toPath())
                    .build();
            list.add(event);
        }
    }

    private void fillWithCreatedFiles(List<File> newSnapshotFiles, List<File> oldSnapshotFiles) {
        List<File> added = getCreatedFiles(newSnapshotFiles, oldSnapshotFiles);
        for (File file : added) {
            Event event = new EventBuilder().type(EventType.CREATE)
                    .path(file.toPath())
                    .build();
            list.add(event);
        }
    }

    private List<File> getCreatedFiles(List<File> newSnapshotFiles, List<File> oldSnapshotFiles) {
        List<File> newCopy = new ArrayList<>(newSnapshotFiles);
        List<File> oldCopy = new ArrayList<>(oldSnapshotFiles);
        oldCopy.retainAll(newCopy);
        return newCopy;
    }

    private List<File> getDeletedFiles(List<File> newSnapshotFiles, List<File> oldSnapshotFiles) {
        List<File> newCopy = new ArrayList<>(newSnapshotFiles);
        List<File> oldCopy = new ArrayList<>(oldSnapshotFiles);
        newCopy.retainAll(oldCopy);
        return oldCopy;
    }

}
