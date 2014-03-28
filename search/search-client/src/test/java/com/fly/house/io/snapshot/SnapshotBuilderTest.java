package com.fly.house.io.snapshot;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by dimon on 1/30/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class SnapshotBuilderTest {

    @Mock
    private File file;
    @Mock
    private Path pathMock;
    private SnapshotBuilder builder;
    private File snapshotDir;
    private Path pathToDirectory;


    @Before
    public void setUp() {
        snapshotDir = new File("./snapshots");
        pathToDirectory = Paths.get("path");
        snapshotDir.mkdir();
        pathToDirectory.toFile().mkdir();

        builder = new SnapshotBuilder(pathToDirectory);
    }

    @After
    public void tearDown() {
        File[] files = snapshotDir.listFiles();
        for (File file1 : files) {
            file1.delete();
        }
        snapshotDir.delete();
        pathToDirectory.toFile().delete();
    }

    @Test
    public void getFreshSnapshotShouldCreateAndSetNewFreshSnapshot() {
        Snapshot freshSnapshot = builder.getFreshSnapshot();
        assertNotNull(freshSnapshot);
    }

    @Test
    public void getFreshSnapshotShouldCreateEmptyFreshSnapshotWhenFolderEmptyToo() {
        Snapshot freshSnapshot = builder.getFreshSnapshot();
        assertThat(freshSnapshot.getFiles().isEmpty(), is(true));
    }

    @Test
    public void getStaleSnapshotShouldCreateAndSetNewSnapshotWhenSteelSnapshotDoesNotExist() {
        Snapshot steelSnapshot = builder.getStaleSnapshot();
        assertNotNull(steelSnapshot);
    }

    @Test
    public void getStaleSnapshotShouldLoadSnapshotWhenSteelSnapshotExists() throws IOException {
        Path snapshotPath = snapshotDir.toPath();
        saveSnapshot(snapshotPath);

        Snapshot steelSnapshot = builder.getStaleSnapshot();

        assertThat(steelSnapshot.getFiles().size(), is(3));
    }

    @Test
    public void save() {
        List<Path> list = listOf("file1", "file2");
        Snapshot snapshot = new Snapshot(list);

        builder.save(snapshot);

        Path resolve = snapshotDir.toPath().resolve(builder.hash());
        assertThat(resolve.toFile().exists(), is(true));
    }

    private List<Path> listOf(String... paths) {
        return Stream.of(paths)
                .map(s -> Paths.get(s))
                .collect(toList());
    }

    private void saveSnapshot(Path path) throws IOException {
        List<Path> list = listOf("file1", "file2", "file3");
        Snapshot snapshot = new Snapshot(list);
        Path name = path.resolve(builder.hash());
        try (OutputStream outputStream = Files.newOutputStream(name);
             ObjectOutputStream ios = new ObjectOutputStream(outputStream)) {
            ios.writeObject(snapshot);
        }
    }


}
