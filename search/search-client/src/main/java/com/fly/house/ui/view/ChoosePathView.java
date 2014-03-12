package com.fly.house.ui.view;

import com.fly.house.ui.presenter.ChoosePathPresenter;
import com.fly.house.ui.view.api.View;

import javax.swing.*;

/**
 * Created by dimon on 3/7/14.
 */
public interface ChoosePathView extends View<ChoosePathPresenter> {

    JFileChooser getFileChooser();

    JTextArea getPathArea();

}
