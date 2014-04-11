package com.fly.house.fileshare;

import com.fly.house.core.event.SystemLoginEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by dimon on 4/9/14.
 */
@Component
public class FileShareLoginEventHandler implements ApplicationListener<SystemLoginEvent> {

    @Autowired
    private FileShareServer server;

    private static Logger logger = LoggerFactory.getLogger(FileShareLoginEventHandler.class);

    @Override
    public void onApplicationEvent(SystemLoginEvent systemLoginEvent) {
        logger.debug("{} handle the login event and start initialization", getClass().getName());
        try {
            server.start();
        } catch (Exception e) {
            logger.warn("Exception occur while initializing file share system", e);
        }
    }

}
