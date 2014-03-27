package com.fly.house.io.snapshot;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by dimon on 1/30/14.
 */
public class Snapshot implements Externalizable {

    private List<Path> files;

    public Snapshot() {
    }

    public Snapshot(List<Path> files) {
        this.files = files;
    }

    public List<Path> getFiles() {
        return files;
    }

    public void setFiles(List<Path> files) {
        this.files = files;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        List<String> paths = files.stream()
                .map(Path::toString)
                .collect(toList());

        out.writeObject(paths);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        List<String> paths = (List<String>) in.readObject();
        System.out.println(paths);
        files = paths.stream().map(s -> Paths.get(s)).collect(toList());
    }
}
