package com.fly.house.ui.view;

import com.fly.house.ui.presenter.PathsPresenter;
import com.fly.house.ui.qualifier.View;
import com.fly.house.ui.view.api.AbstractView;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.PAGE_START;

/**
 * Created by dimon on 3/8/14.
 */
@View
public class PathsViewImpl extends AbstractView<PathsPresenter> implements PathsView {

    private static final String[] HEADER = {"Path", "Action"};
    private final JButton unregisterButton = new JButton("Unregister");
    private JTable table = new JTable();

    public PathsViewImpl() {
        super(500, 475);
        add(table.getTableHeader(), PAGE_START);
        add(table, CENTER);
        add(unregisterButton, BorderLayout.SOUTH);
        addUnregisterListener();
    }

    @Override
    public JTable getTable() {
        return table;
    }

    @Override
    public String[] getHeader() {
        return HEADER;
    }

    private void addUnregisterListener() {
        unregisterButton.addActionListener(e -> presenter.onUnregisterPath());
    }
}
