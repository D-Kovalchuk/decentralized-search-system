package com.fly.house.ui.view;

import com.fly.house.ui.presenter.AuthorizationPresenter;

import javax.swing.*;

/**
 * Created by dimon on 3/6/14.
 */
public interface AuthorizationView {
    JTextField getPasswordField();

    JTextField getLoginField();

    JPanel asJPanel();

    void setPresenter(AuthorizationPresenter presenter);
}
