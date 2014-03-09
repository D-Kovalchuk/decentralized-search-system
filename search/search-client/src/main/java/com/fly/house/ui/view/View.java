package com.fly.house.ui.view;

import com.fly.house.ui.presenter.Presenter;

import javax.swing.*;

/**
 * Created by dimon on 3/9/14.
 */
public interface View<P extends Presenter> {

    JPanel asJPanel();

    void setPresenter(P presenter);

}
