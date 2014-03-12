package com.fly.house.ui.view;

import com.fly.house.ui.presenter.HistoryPresenter;
import com.fly.house.ui.view.api.View;

import javax.swing.*;

/**
 * Created by dimon on 3/9/14.
 */
public interface HistoryView extends View<HistoryPresenter> {

    JTable getTable();

    String[] getHeader();
}
