package com.fly.house.authentication.ip.handler;

import com.fly.house.authentication.ip.notifier.IpNotifierConnectionManager;
import com.fly.house.core.event.SystemLoginEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by dimon on 4/18/14.
 */
@Component
public class IpNotifierLoginEventHandler implements ApplicationListener<SystemLoginEvent> {

    @Autowired
    private IpNotifierConnectionManager client;

    private static Logger logger = LoggerFactory.getLogger(IpNotifierLoginEventHandler.class);

    @Override
    public void onApplicationEvent(SystemLoginEvent event) {
        logger.debug("{} handle the login event and start initialization", getClass().getName());
        client.connect();
    }

}
