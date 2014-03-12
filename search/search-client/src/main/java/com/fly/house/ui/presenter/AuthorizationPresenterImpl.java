package com.fly.house.ui.presenter;


import com.fly.house.authentication.Authorization;
import com.fly.house.io.exceptions.WatchServiceException;
import com.fly.house.ui.event.ChoosePathEvent;
import com.fly.house.ui.presenter.api.AbstractPresenter;
import com.fly.house.ui.qualifier.Presenter;
import com.fly.house.ui.view.AuthorizationView;
import com.fly.house.ui.view.ViewContainer;
import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dimon on 3/6/14.
 */
@Presenter
public class AuthorizationPresenterImpl extends AbstractPresenter<AuthorizationView> implements AuthorizationPresenter {

    private static Logger logger = LoggerFactory.getLogger(AuthorizationPresenterImpl.class);
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
            logger.debug("fired ChoosePathEvent");
            eventBus.post(new ChoosePathEvent());
        } catch (WatchServiceException e) {
            String message = e.getMessage();
            logger.debug("show error message");
            view.getErrorLabel().setText(message);
        }
    }

    @Override
    public void go() {
        super.go();
        menu.setVisible(false);
    }
}
