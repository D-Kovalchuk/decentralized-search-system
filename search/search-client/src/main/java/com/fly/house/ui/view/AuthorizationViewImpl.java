package com.fly.house.ui.view;

import com.fly.house.ui.presenter.AuthorizationPresenter;
import com.fly.house.ui.qualifier.View;
import com.fly.house.ui.view.api.AbstractView;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dimon on 3/6/14.
 */
@View
public class AuthorizationViewImpl extends AbstractView<AuthorizationPresenter> implements AuthorizationView {

    private JButton authButton = new JButton("login");
    private JTextField loginField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JLabel errorLabel = new JLabel();

    public AuthorizationViewImpl() {
        super(new GridLayout(4, 2));
        setPreferredSize(new Dimension(500, 200));
        add(new Label("Login: "));
        add(loginField);
        loginField.setPreferredSize(new Dimension(100, 20));
        add(new Label("Password: "));
        add(passwordField);
        add(new Label());
        add(authButton);
        add(errorLabel);
        addLoginButtonClickedListener();
    }

    @Override
    public JTextField getPasswordField() {
        return passwordField;
    }

    @Override
    public JTextField getLoginField() {
        return loginField;
    }

    @Override
    public JLabel getErrorLabel() {
        return errorLabel;
    }

    public void addLoginButtonClickedListener() {
        authButton.addActionListener(e -> presenter.onLogin());
    }

}
