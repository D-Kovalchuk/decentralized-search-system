package com.fly.house.ui.view;

import com.fly.house.ui.presenter.AuthorizationPresenter;

import javax.swing.*;

/**
 * Created by dimon on 3/6/14.
 */
public interface AuthorizationView extends View<AuthorizationPresenter> {

    JTextField getPasswordField();

    JTextField getLoginField();

    JLabel getErrorLabel();
}
