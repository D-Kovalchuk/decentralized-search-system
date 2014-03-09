package com.fly.house.ui.view;

import com.fly.house.ui.presenter.PathsPresenter;

import javax.swing.*;

/**
 * Created by dimon on 3/8/14.
 */
public interface PathsView extends View<PathsPresenter> {

    JTable getTable();

    String[] getHeader();

}
