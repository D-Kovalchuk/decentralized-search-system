package com.fly.house.io;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Created by dimon on 3/31/14.
 */
public class FileFilter {

    private static final String[] mimeTypes = {
            "application/pdf", "text/plain", "text/html", "text/rtf", "text/example",
            "text/csv", "application/atom+xml", "application/pdf", "application/xml", "text/xml"
    };

    private static Logger logger = LoggerFactory.getLogger(FileFilter.class);

    public static boolean isAcceptedType(Path path) {
        Tika tika = new Tika();
        try {
            String mimeType = tika.detect(path.toFile());

            return Arrays.stream(mimeTypes)
                    .anyMatch(s -> s.equals(mimeType));
        } catch (IOException e) {
            logger.warn("cannot open file", e);
        }
        return false;
    }
}
