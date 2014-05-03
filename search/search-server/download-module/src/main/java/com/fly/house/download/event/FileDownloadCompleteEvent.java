package com.fly.house.download.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by dimon on 03.05.14.
 */
public class FileDownloadCompleteEvent extends ApplicationEvent {

    public FileDownloadCompleteEvent(Object object) {
        super(object);
    }

}
