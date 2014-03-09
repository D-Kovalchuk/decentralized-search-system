package com.fly.house.ui.event;

import com.fly.house.ui.presenter.AuthorizationPresenter;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dimon on 3/7/14.
 */
@Component
public class LogoutEventHandler {

    @Autowired
    private AuthorizationPresenter presenter;

    @Subscribe
    public void onLogout(LogoutEvent event) {
        presenter.go();
    }
}
