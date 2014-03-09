package com.fly.house.ui.presenter;

import com.fly.house.authentication.Authorization;
import com.fly.house.io.exceptions.WatchServiceException;
import com.fly.house.ui.event.LoginEvent;
import com.fly.house.ui.view.AuthorizationView;
import com.fly.house.ui.view.ViewContainer;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dimon on 3/6/14.
 */
@Component
public class AuthorizationPresenterImpl extends AbstractPresenter<AuthorizationView> implements AuthorizationPresenter {

    private Authorization authorization;

    @Autowired
    protected AuthorizationPresenterImpl(EventBus eventBus, AuthorizationView view,
                                         ViewContainer container, Authorization authorization) {
        super(eventBus, view, container);
        this.authorization = authorization;
    }

    @Override
    public void onLogin() {
        String login = view.getLoginField().getText();
        String password = view.getPasswordField().getText();
        try {
            authorization.authentication(login, password);
            eventBus.post(new LoginEvent());
        } catch (WatchServiceException e) {
            //fixme
        }
    }

    @Override
    public void go() {
        super.go();
        menu.setVisible(false);
    }
}
