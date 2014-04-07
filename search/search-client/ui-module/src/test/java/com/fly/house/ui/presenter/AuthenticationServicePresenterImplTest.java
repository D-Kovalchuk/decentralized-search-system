package com.fly.house.ui.presenter;

import com.fly.house.authentication.AuthenticationService;
import com.fly.house.authentication.exception.AuthorizationException;
import com.fly.house.ui.event.ChoosePathEvent;
import com.fly.house.ui.view.AuthorizationView;
import com.fly.house.ui.view.ViewContainer;
import com.google.common.eventbus.EventBus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.swing.*;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by dimon on 3/10/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServicePresenterImplTest {

    @Mock
    private EventBus eventBus;
    @Mock
    private AuthorizationView view;
    @Mock
    private ViewContainer container;
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private AuthorizationPresenterImpl presenter;
    @Mock
    private JTextField login;
    @Mock
    private JTextField password;
    @Mock
    private JLabel errorLabel;


    @Test
    public void shouldSetPresenterIntoView() {
        verify(view).setPresenter(presenter);
    }

    @Test
    public void onLoginShouldRetrieveInfoFromFields() throws Exception {
        setUpViewBehavior();

        presenter.onLogin();

        verify(view).getLoginField();
        verify(login).getText();
        verify(view).getPasswordField();
        verify(password).getText();
    }

    @Test
    public void onLoginShouldAuthenticate() throws Exception {
        setUpViewBehavior();

        presenter.onLogin();

        verify(authenticationService).authentication("login", "password");
    }

    @Test
    public void onLoginShouldGoToChoosePathsView() {
        setUpViewBehavior();

        presenter.onLogin();

        verify(eventBus).post(any(ChoosePathEvent.class));
    }

    @Test
    public void onLoginShouldShowErrorMessage() {
        setUpViewBehavior();
        doThrow(new AuthorizationException("error message")).when(authenticationService).authentication(anyString(), anyString());

        presenter.onLogin();

        verify(errorLabel).setText("error message");
    }

    private void setUpViewBehavior() {
        when(view.getLoginField()).thenReturn(login);
        when(view.getPasswordField()).thenReturn(password);
        when(login.getText()).thenReturn("login");
        when(password.getText()).thenReturn("password");
        when(view.getErrorLabel()).thenReturn(errorLabel);
    }

}
