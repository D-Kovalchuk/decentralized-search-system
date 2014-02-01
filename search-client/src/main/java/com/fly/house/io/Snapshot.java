package com.fly.house.io;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * Created by dimon on 1/30/14.
 */
public class Snapshot implements Serializable {
    private List<File> files;

    public Snapshot(List<File> files) {
        this.files = files;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
