package com.fly.house.io.repositories;

import com.fly.house.io.exceptions.DirectoryNotFoundException;
import com.fly.house.io.exceptions.NotDirectoryException;
import com.fly.house.io.exceptions.PathNotRegisteredException;
import com.fly.house.io.exceptions.PathRegisteredException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.charset.Charset.defaultCharset;
import static java.nio.file.Files.newBufferedReader;
import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

/**
 * Created by dimon on 1/26/14.
 */
public class FilePathRepositoryTest {

    private PathRepository pathRepository;
    private Path veryFirstPath;
    private Path anotherPath;
    private Path relativePath;
    private Path notRealPath;
    private Path pathToFile;
    private Path pathRegister;

    @Before
    public void setUp() throws Exception {
        pathRegister = Paths.get("paths.pth");
        veryFirstPath = Paths.get("share").toAbsolutePath();
        anotherPath = Paths.get("anotherFolder").toAbsolutePath();
        relativePath = Paths.get("relative");
        pathToFile = Paths.get("file").toAbsolutePath();
        createFolders();
        notRealPath = Paths.get("not/real/path");

        pathRepository = new FilePathRepository(pathRegister);
    }

    @After
    public void tearDown() throws Exception {
        pathRegister.toFile().delete();
        veryFirstPath.toFile().delete();
        anotherPath.toFile().delete();
        relativePath.toFile().delete();
        pathToFile.toFile().delete();
    }

    @Test
    public void shouldCreateNewFileWhenNoPathsWhereAdded() {
        pathRepository.add(veryFirstPath);

        assertThat(pathRegister.toFile().exists(), equalTo(true));
    }

    @Test
    public void shouldSaveNewPathInSystemFile() throws IOException {
        pathRepository.add(veryFirstPath);

        String savedPath = readLines();

        assertThat(Paths.get(savedPath), equalTo(veryFirstPath));
    }

    @Test
    public void shouldSaveTowOrMorePathsSeparatedByEnter() throws IOException {
        pathRepository.add(veryFirstPath);
        pathRepository.add(anotherPath);

        String content = readLines();

        assertThat(content, equalTo(veryFirstPath.toAbsolutePath() +
                "" + anotherPath.toAbsolutePath()));
    }

    @Test
    public void getPathsShouldReturnEmptyListWhenPathsIsEmpty() {
        List<Path> paths = pathRepository.getPaths();

        assertThat(paths.isEmpty(), is(true));
    }

    @Test
    public void getPathsShouldReturnOneElement() {
        pathRepository.add(veryFirstPath);

        List<Path> paths = pathRepository.getPaths();

        assertThat(paths.size(), equalTo(1));
    }

    @Test
    public void getPathsShouldReturnTowElement() {
        pathRepository.add(veryFirstPath);
        pathRepository.add(anotherPath);

        List<Path> paths = pathRepository.getPaths();

        assertThat(paths.size(), equalTo(2));
    }


    @Test(expected = PathNotRegisteredException.class)
    public void removeShouldThrowExceptionWhenFileDoesNotExist() {
        pathRepository.remove(veryFirstPath);
    }

    @Test(expected = PathNotRegisteredException.class)
    public void removeShouldThrowExceptionWhenFileIsEmpty() throws IOException {
        pathRegister.toFile().createNewFile();

        pathRepository.remove(veryFirstPath);
    }

    @Test
    public void removeShouldRemoveSpecifiedFiles() throws IOException {
        pathRepository.add(veryFirstPath);
        pathRepository.add(anotherPath);

        pathRepository.remove(veryFirstPath);

        String lines = readLines();

        assertThat(lines, equalTo(anotherPath.toString()));
    }

    @Test(expected = PathRegisteredException.class)
    public void addShouldThrowExceptionWhenPathIsAlreadyRegistered() {
        pathRepository = spy(new FilePathRepository(pathRegister));
        doReturn(asList(veryFirstPath)).when(pathRepository).getPaths();

        pathRepository.add(veryFirstPath);
        pathRepository.add(veryFirstPath);
    }

    @Test
    public void addShouldWriteAbsolutePath() throws IOException {
        pathRepository.add(relativePath);

        String lines = readLines();

        String absolutePath = relativePath.toAbsolutePath().toString();
        assertThat(lines, equalTo(absolutePath));
    }

    @Test(expected = DirectoryNotFoundException.class)
    public void addShouldThrowExceptionWhenPathDoesNotExist() {
        pathRepository.add(notRealPath);
    }

    @Test(expected = NotDirectoryException.class)
    public void addShouldThrowExceptionWhenWasSpecifiedPathToFile() {
        pathRepository.add(pathToFile);
    }

    @Test(expected = PathNotRegisteredException.class)
    public void removeShouldThrowExceptionWhenPathHasNotBeenAddedYet() throws IOException {
        pathRegister.toFile().createNewFile();
        pathRepository = spy(new FilePathRepository(pathRegister));
        doReturn(asList(veryFirstPath)).when(pathRepository).getPaths();

        pathRepository.remove(anotherPath);
    }

    @Test(expected = DirectoryNotFoundException.class)
    public void removeShouldThrowExceptionWhenPathDoesNotExist() {
        pathRepository.remove(notRealPath);
    }

    @Test(expected = NotDirectoryException.class)
    public void removeShouldThrowExceptionWhenWasSpecifiedPathToFile() {
        pathRepository.remove(pathToFile);
    }

    private void createFolders() throws IOException {
        veryFirstPath.toFile().mkdir();
        anotherPath.toFile().mkdir();
        relativePath.toFile().mkdir();
        pathToFile.toFile().createNewFile();
    }

    private String readLines() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = newBufferedReader(pathRegister, defaultCharset())) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }


}
