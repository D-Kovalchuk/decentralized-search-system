package com.fly.house.ui.view.api;

import com.fly.house.ui.presenter.api.Presenter;
import com.fly.house.ui.view.TopMenu;

import javax.swing.*;

/**
 * Created by dimon on 3/9/14.
 */
public interface View<P extends Presenter> {

    JPanel asJPanel();

    void setPresenter(P presenter);

    TopMenu getTopMenu();

}
