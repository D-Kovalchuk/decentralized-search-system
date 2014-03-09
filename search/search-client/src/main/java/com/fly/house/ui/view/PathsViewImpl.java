package com.fly.house.ui.view;

import com.fly.house.ui.presenter.PathsPresenter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.PAGE_START;

/**
 * Created by dimon on 3/8/14.
 */
@Component
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
        unregisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenter.onUnregisterPath();
            }
        });
    }
}
