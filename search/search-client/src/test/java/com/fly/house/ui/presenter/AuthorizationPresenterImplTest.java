package com.fly.house.ui.presenter;

import com.fly.house.authentication.Authorization;
import com.fly.house.io.exceptions.WatchServiceException;
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

import static org.mockito.Mockito.*;

/**
 * Created by dimon on 3/10/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthorizationPresenterImplTest {

    @Mock
    private EventBus eventBus;
    @Mock
    private AuthorizationView view;
    @Mock
    private ViewContainer container;
    @Mock
    private Authorization authorization;
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

        verify(authorization).authentication("login", "password");
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
        doThrow(new WatchServiceException("error message")).when(authorization).authentication(anyString(), anyString());

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
