package com.fly.house.ui.view;

import com.fly.house.ui.presenter.AuthorizationPresenter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by dimon on 3/6/14.
 */
@Component
public class AuthorizationViewImpl extends AbstractView<AuthorizationPresenter> implements AuthorizationView {

    private JButton authButton = new JButton("login");
    private JTextField loginField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();

    public AuthorizationViewImpl() {
        super(new GridLayout(3, 2));
        setPreferredSize(new Dimension(500, 200));
        add(new Label("Login: "));
        add(loginField);
        loginField.setPreferredSize(new Dimension(100, 20));
        add(new Label("Password: "));
        add(passwordField);
        add(new Label());
        add(authButton);
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

    public void addLoginButtonClickedListener() {
        authButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenter.onLogin();
            }
        });
    }

}
