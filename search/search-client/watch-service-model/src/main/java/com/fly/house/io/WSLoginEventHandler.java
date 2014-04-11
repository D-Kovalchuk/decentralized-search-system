package com.fly.house.io;

import com.fly.house.core.event.SystemLoginEvent;
import com.fly.house.io.exceptions.WatchServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by dimon on 4/9/14.
 */
@Component
public class WSLoginEventHandler implements ApplicationListener<SystemLoginEvent> {

    @Autowired
    private WSInitializer initializer;

    private static Logger logger = LoggerFactory.getLogger(WSLoginEventHandler.class);

    @Override
    public void onApplicationEvent(SystemLoginEvent loginEvent) {
        logger.debug("{} handle the login event and start initialization", getClass().getName());
        try {
            initializer.initialize();
        } catch (WatchServiceException e) {
            logger.warn("Exception occur while initializing Watch Service", e);
        }
    }
}
