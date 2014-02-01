package com.fly.house.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimon on 1/30/14.
 */
public class SnapshotComparator {


    public SnapshotComparator() {

    }

    public List<Event> getDiff(Snapshot newSnapshot, Snapshot oldSnapshot) {
        List<Event> list = new ArrayList<>();

        List<File> newSnapshotFiles = newSnapshot.getFiles();
        List<File> oldSnapshotFiles = oldSnapshot.getFiles();

        List<File> added = getCreatedFiles(newSnapshotFiles, oldSnapshotFiles);
        List<File> removed = getDeletedFiles(newSnapshotFiles, oldSnapshotFiles);


        for (File file : added) {
            Event event = Event.create(EventType.CREATE)
                    .withNewPath(file.toPath());
            list.add(event);
        }

        for (File file : removed) {
            Event event = Event.create(EventType.DELETE)
                    .withOldPath(file.toPath());
            list.add(event);
        }
        return list;
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
