package com.fly.house.ui.event;

import com.fly.house.ui.presenter.AuthorizationPresenter;
import com.fly.house.ui.qualifier.Handler;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dimon on 3/6/14.
 */
@Handler
public class LoginEventHandler {

    private static Logger logger = LoggerFactory.getLogger(LoginEventHandler.class);

    @Autowired
    private AuthorizationPresenter presenter;

    @Subscribe
    public void onLogin(LoginEvent event) {
        logger.debug("handling {}", event.getClass().getName());
        presenter.go();
    }

}
