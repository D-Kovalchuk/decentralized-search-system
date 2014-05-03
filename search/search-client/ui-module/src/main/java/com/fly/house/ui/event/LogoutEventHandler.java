package com.fly.house.ui.event;

import com.fly.house.authentication.AuthenticationService;
import com.fly.house.core.event.SystemEventPublisher;
import com.fly.house.core.event.SystemLogoutEvent;
import com.fly.house.ui.presenter.AuthorizationPresenter;
import com.fly.house.ui.qualifier.Handler;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dimon on 3/7/14.
 */
@Handler
public class LogoutEventHandler {

    private static Logger logger = LoggerFactory.getLogger(LogoutEventHandler.class);

    @Autowired
    private AuthorizationPresenter presenter;

    @Autowired
    private SystemEventPublisher publisher;

    @Autowired
    private AuthenticationService service;

    @Subscribe
    public void onLogout(LogoutEvent event) {
        logger.debug("handling {}", event.getClass().getName());
        publisher.publish(new SystemLogoutEvent("logout"));
        service.logout();
        logger.debug("Logged out form the system");
        presenter.go();
    }
}
