package com.fly.house.io;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import static java.nio.file.Files.newInputStream;
import static java.nio.file.Files.newOutputStream;
import static java.util.Arrays.asList;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

/**
 * Created by dimon on 1/30/14.
 */
public class SnapshotBuilder {

    private Path snapshotFolder = Paths.get("search-client/src/test/resources/snapshots/");
    private String hash;
    private Path path;

    public SnapshotBuilder(Path path) {
        this.path = path;
        this.hash = hash();
    }

    public Snapshot getFreshSnapshot() {
        File file = path.toFile();
        File[] files = file.listFiles();
        return new Snapshot(asList(files));
    }

    public Snapshot getSteelSnapshot() {
        Path hashFileName = snapshotFolder.resolve(hash);
        if (!hashFileName.toFile().exists()) {
            return new Snapshot(Collections.<File>emptyList());
        }
        try (InputStream outputStream = newInputStream(hashFileName);
             ObjectInputStream ios = new ObjectInputStream(outputStream)) {
            return (Snapshot) ios.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void save(Snapshot snapshot) {
        Path name = snapshotFolder.resolve(hash);
        try (OutputStream outputStream = newOutputStream(name);
             ObjectOutputStream ios = new ObjectOutputStream(outputStream)) {
            ios.writeObject(snapshot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String hash() {
        String pathString = path.toAbsolutePath().toString();
        return md5Hex(pathString);

    }

}
