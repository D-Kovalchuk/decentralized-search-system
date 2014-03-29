package com.fly.house.io.repositories;


import com.fly.house.io.exceptions.PathNotRegisteredException;
import com.fly.house.io.repositories.api.AbstractPathRepository;
import com.fly.house.io.repositories.api.FileRepo;
import com.fly.house.io.repositories.api.PathRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.Charset.defaultCharset;
import static java.nio.file.Files.newBufferedReader;
import static java.nio.file.Files.newBufferedWriter;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.util.Collections.emptyList;

/**
 * Created by dimon on 1/26/14.
 */
@FileRepo
@PathRepo
public class FilePathRepository extends AbstractPathRepository {

    protected Path pathsFile;
    private static Logger logger = LoggerFactory.getLogger(FilePathRepository.class);

    @Autowired
    public FilePathRepository(@Qualifier("pathsListFile") Path file) {
        pathsFile = file;
    }

    @Override
    public void add(final Path path) {
        super.add(path);
        String separator = null;
        if (fileExists()) {
            separator = "\n";
        }
        try (BufferedWriter writer = newBufferedWriter(pathsFile, defaultCharset(), APPEND, CREATE)) {
            if (separator != null) {
                writer.write(separator);
            }
            Path absolutePath = path.toAbsolutePath();
            writer.write(absolutePath.toString());
            logger.debug("Path {} has been written into {}", path.toString(), pathsFile.toString());
        } catch (IOException e) {
            logger.warn("Path registration has been failed", e);
        }
    }

    @Override
    public List<Path> getPaths() {
        if (!fileExists()) {
            logger.info("Paths list is empty");
            return emptyList();
        }
        List<Path> paths = new ArrayList<>();
        try (BufferedReader reader = newBufferedReader(pathsFile, defaultCharset())) {
            String line;
            while ((line = reader.readLine()) != null) {
                paths.add(Paths.get(line));
                logger.debug("Path {} has been retrieved", line);
            }
        } catch (IOException e) {
            logger.warn("Cannot read from paths list file", e);
        }
        return paths;
    }

    @Override
    public void remove(final Path path) {
        super.remove(path);
        List<Path> paths = getPaths();
        if (!fileExists() || paths.isEmpty()) {
            throw new PathNotRegisteredException("No path has been added to the system yet.");
        }

        if (!paths.contains(path)) {
            throw new PathNotRegisteredException("Path hasn't been add yet");
        }
        paths.remove(path);
        logger.debug("Path {} has been removed", path.toString());

        try (BufferedWriter writer = newBufferedWriter(pathsFile, defaultCharset())) {
            for (Path path1 : paths) {
                writer.write(path1.toString());
            }
        } catch (IOException e) {
            logger.warn("Cannot remove from paths list file", e);
        }
    }

    private boolean fileExists() {
        return pathsFile.toFile().exists();
    }

}
