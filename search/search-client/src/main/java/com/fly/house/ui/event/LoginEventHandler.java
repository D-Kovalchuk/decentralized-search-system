package com.fly.house.ui.event;

import com.fly.house.ui.presenter.AuthorizationPresenter;
import com.fly.house.ui.qualifier.Handler;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dimon on 3/6/14.
 */
@Handler
public class LoginEventHandler {

    @Autowired
    private AuthorizationPresenter presenter;

    @Subscribe
    public void onLogin(LoginEvent event) {
        presenter.go();
    }

}
