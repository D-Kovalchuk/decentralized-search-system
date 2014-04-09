package com.fly.house.ui.presenter;


import com.fly.house.authentication.AuthenticationService;
import com.fly.house.authentication.exception.AuthorizationException;
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
    private AuthenticationService authenticationService;

    @Autowired
    protected AuthorizationPresenterImpl(EventBus eventBus, AuthorizationView view,
                                         ViewContainer container, AuthenticationService authenticationService) {
        super(eventBus, view, container);
        this.authenticationService = authenticationService;
    }

    @Override
    public void onLogin() {
        String login = view.getLoginField().getText();
        String password = view.getPasswordField().getText();
        try {
            authenticationService.authentication(login, password);
            logger.debug("fired ChoosePathEvent");
            eventBus.post(new ChoosePathEvent());
        } catch (AuthorizationException e) {
            String message = e.getMessage();
            logger.debug("show error message", e);
            view.getErrorLabel().setText(message);
        }
    }

    @Override
    public void go() {
        super.go();
        view.getTopMenu().setVisible(false);
    }
}
