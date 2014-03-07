package com.fly.house.ui.event;

import com.fly.house.ui.presenter.AuthorizationPresenter;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dimon on 3/6/14.
 */
@Component
public class LoginEventHandler {

    @Autowired
    private AuthorizationPresenter presenter;

    @Subscribe
    public void onLogin(LoginEvent event) {
        presenter.go();
    }

}
