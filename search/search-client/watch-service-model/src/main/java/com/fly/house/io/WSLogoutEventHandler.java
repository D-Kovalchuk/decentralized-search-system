package com.fly.house.io;

import com.fly.house.core.event.SystemLogoutEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by dimon on 4/9/14.
 */
@Component
public class WSLogoutEventHandler implements ApplicationListener<SystemLogoutEvent> {

    @Autowired
    private InMemoryWSStorage ws;

    private static Logger logger = LoggerFactory.getLogger(WSLogoutEventHandler.class);

    @Override
    public void onApplicationEvent(SystemLogoutEvent logoutEvent) {
        logger.info("{} handle the event", getClass().getName());
        logger.info("start cleaning resources up");
        ws.cleanUp();
    }

}
