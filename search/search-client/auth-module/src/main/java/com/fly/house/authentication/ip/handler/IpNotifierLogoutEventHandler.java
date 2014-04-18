package com.fly.house.authentication.ip.handler;

import com.fly.house.authentication.ip.notifier.IpNotifierConnectionManager;
import com.fly.house.core.event.SystemLogoutEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by dimon on 4/18/14.
 */
@Component
public class IpNotifierLogoutEventHandler implements ApplicationListener<SystemLogoutEvent> {

    @Autowired
    private IpNotifierConnectionManager client;

    private static Logger logger = LoggerFactory.getLogger(IpNotifierLogoutEventHandler.class);

    @Override
    public void onApplicationEvent(SystemLogoutEvent event) {
        logger.debug("{} handle the logout event and start initialization", getClass().getName());
        client.destroy();
    }

}
