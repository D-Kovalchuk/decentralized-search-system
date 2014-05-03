package com.fly.house.download.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;

/**
 * Created by dimon on 03.05.14.
 */
public class FileDownloadErrorEvent extends ApplicationEvent {

    private static Logger logger = LoggerFactory.getLogger(FileDownloadErrorEvent.class);

    public FileDownloadErrorEvent(Object source) {
        super(source);
    }

    public FileDownloadErrorEvent(Object file, Throwable ex) {
        this(file);
        logger.warn("Exception occurred while file was downloading or parsing", ex);
    }
}
