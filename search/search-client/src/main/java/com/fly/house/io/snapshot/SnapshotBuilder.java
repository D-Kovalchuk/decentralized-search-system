package com.fly.house.io.snapshot;

import com.fly.house.io.exceptions.SnapshotIOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Files.newInputStream;
import static java.nio.file.Files.newOutputStream;
import static java.util.Collections.emptyList;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

/**
 * Created by dimon on 1/30/14.
 */
public class SnapshotBuilder {

    //TODO move it to config file
    private Path snapshotFolder = Paths.get("./snapshots");
    private String hash;
    private Path path;
    private static Logger logger = LoggerFactory.getLogger(SnapshotBuilder.class);
    public static final Snapshot EMPTY_SNAPSHOT = new Snapshot(emptyList());

    public SnapshotBuilder(Path path) {
        this.path = path;
        this.hash = hash();
    }

    public Snapshot getFreshSnapshot() {
        try (Stream<Path> pathStream = Files.list(path)) {
            List<Path> collect = pathStream.collect(Collectors.toList());
            new Snapshot(collect);
        } catch (IOException e) {
            logger.debug("Directory {} does not exist", path.toString());
            logger.debug("Returning empty snapshot");
            return EMPTY_SNAPSHOT;
        }
        return EMPTY_SNAPSHOT;
    }

    public Snapshot getStaleSnapshot() {
        Path pathToSnapshot = snapshotFolder.resolve(hash);
        if (!pathToSnapshot.toFile().exists()) {
            logger.debug("File {} does not exist", pathToSnapshot.toString());
            logger.debug("Returning empty snapshot");
            return EMPTY_SNAPSHOT;
        }
        try (ObjectInputStream ios = new ObjectInputStream(newInputStream(pathToSnapshot))) {
            return (Snapshot) ios.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.warn("Problems with reading snapshot", e);
            throw new SnapshotIOException("Problems with reading snapshot", e);
        }
    }


    public void save(Snapshot snapshot) {
        Path name = snapshotFolder.resolve(hash);
        logger.debug("Saving snapshot to {}", name.toString());
        try (ObjectOutputStream oos = new ObjectOutputStream(newOutputStream(name))) {
            oos.writeObject(snapshot);
        } catch (IOException e) {
            logger.warn("Problems with saving snapshot", e);
            throw new SnapshotIOException("Problems with saving snapshot", e);
        }
    }

    String hash() {
        logger.debug("Generating hash");
        String pathString = path.toAbsolutePath().toString();
        return md5Hex(pathString);
    }

}
