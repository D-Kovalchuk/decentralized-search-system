package com.fly.house.ui.view;

import com.fly.house.ui.presenter.HistoryPresenter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.PAGE_START;

/**
 * Created by dimon on 3/9/14.
 */
@Component
public class HistoryViewImpl extends AbstractView<HistoryPresenter> implements HistoryView {

    private static final String[] HEADER = {"Path", "Action", "Close"};

    private JTable table = new JTable();
    private JButton applyButton = new JButton("apply");
    private JButton shareButton = new JButton("share");

    public HistoryViewImpl() {
        super(500, 475);
        add(table.getTableHeader(), PAGE_START);
        add(table, CENTER);
        add(applyButton, BorderLayout.SOUTH);
        add(shareButton, BorderLayout.SOUTH);
        addApplyButtonClickedListener();
        addShareButtonClickedListener();
    }

    private void addApplyButtonClickedListener() {
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenter.onApply();
            }
        });
    }

    private void addShareButtonClickedListener() {
        shareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenter.onShare();
            }
        });
    }

    @Override
    public JTable getTable() {
        return table;
    }

    @Override
    public String[] getHeader() {
        return HEADER;
    }
}
