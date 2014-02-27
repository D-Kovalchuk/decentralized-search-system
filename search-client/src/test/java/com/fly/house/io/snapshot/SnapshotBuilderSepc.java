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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by dimon on 1/30/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class SnapshotBuilderSepc {

    @Mock
    private File file;
    @Mock
    private Path pathMock;
    private SnapshotBuilder builder;
    private File snapshotDir;
    private Path pathToDirectory;


    @Before
    public void setUp() {
        snapshotDir = new File("search-client/src/test/resources/snapshots/");
        pathToDirectory = Paths.get("search-client/src/test/resources/path");
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
    public void getFreshSnapshotShouldCreateFullFreshSnapshotWhenFolderFullToo() {
        when(file.listFiles()).thenReturn(new File[]{new File("file1"), new File("file2")});
        when(pathMock.toFile()).thenReturn(file);
        when(pathMock.toAbsolutePath()).thenReturn(pathMock);

        builder = new SnapshotBuilder(pathMock);
        Snapshot freshSnapshot = builder.getFreshSnapshot();

        assertThat(freshSnapshot.getFiles().size(), is(2));
    }

    @Test
    public void getSteelSnapshotShouldCreateAndSetNewSnapshotWhenSteelSnapshotDoesNotExist() {
        Snapshot steelSnapshot = builder.getSteelSnapshot();
        assertNotNull(steelSnapshot);
    }

    @Test
    public void getSteelSnapshotShouldLoadSnapshotWhenSteelSnapshotExists() throws IOException {
        Path snapshotPath = snapshotDir.toPath();
        saveSnapshot(snapshotPath);

        Snapshot steelSnapshot = builder.getSteelSnapshot();

        assertThat(steelSnapshot.getFiles().size(), is(3));
    }

    @Test
    public void save() {
        List<File> list = new ArrayList<>();
        list.add(new File("file1"));
        list.add(new File("file2"));
        Snapshot snapshot = new Snapshot(list);

        builder.save(snapshot);

        Path resolve = snapshotDir.toPath().resolve(builder.hash());
        assertThat(resolve.toFile().exists(), is(true));
    }

    private void saveSnapshot(Path path) throws IOException {
        List<File> list = new ArrayList<>();
        list.add(new File("file1"));
        list.add(new File("file2"));
        list.add(new File("file3"));
        Snapshot snapshot = new Snapshot(list);
        Path name = path.resolve(builder.hash());
        try (OutputStream outputStream = Files.newOutputStream(name);
             ObjectOutputStream ios = new ObjectOutputStream(outputStream)) {
            ios.writeObject(snapshot);
        }
    }


}
