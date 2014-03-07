package com.fly.house.ui.presenter;

import com.fly.house.authentication.Authorization;
import com.fly.house.io.exceptions.WatchServiceException;
import com.fly.house.ui.event.LoginEvent;
import com.fly.house.ui.view.AuthorizationView;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dimon on 3/6/14.
 */
@Component
//@Scope(SCOPE_PROTOTYPE)
public class AuthorizationPresenterImpl implements AuthorizationPresenter {

    private Authorization authorization;
    private AuthorizationView authorizationView;
    private EventBus eventBus;
    private JPanel container;

    @Autowired
    public AuthorizationPresenterImpl(EventBus eventBus, AuthorizationView authorizationView, Authorization authorization) {
        this.eventBus = eventBus;
        this.authorizationView = authorizationView;
        this.authorization = authorization;
        authorizationView.setPresenter(this);
    }

    @Override
    public void onLogin() {
        String login = authorizationView.getLoginField().getText();
        String password = authorizationView.getPasswordField().getText();
        try {
            authorization.authentication(login, password);
            eventBus.post(new LoginEvent());
        } catch (WatchServiceException e) {
            //fixme
        }
    }

    @Autowired
    public void setContainer(JPanel container) {
        this.container = container;
    }

    @Override
    public void go() {
        container.removeAll();
        container.add(authorizationView.asJPanel(), BorderLayout.CENTER);
        container.updateUI();
    }

}
