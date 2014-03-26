package com.fly.house.ui.view;

import com.fly.house.ui.presenter.ChoosePathPresenter;
import com.fly.house.ui.qualifier.View;
import com.fly.house.ui.view.api.AbstractView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JFileChooser.DIRECTORIES_ONLY;

/**
 * Created by dimon on 3/7/14.
 */
@View
public class ChoosePathViewImpl extends AbstractView<ChoosePathPresenter> implements ChoosePathView {

    private JFileChooser fileChooser = new JFileChooser();
    private JTextArea pathArea = new JTextArea();
    private JButton okButton = new JButton("Ok");
    private JButton chooseButton = new JButton("Choose button");


    public ChoosePathViewImpl() {
        super(500, 475);
        setFileChooseConfig();
        add(chooseButton, BorderLayout.NORTH);
        pathArea.setEditable(false);
        add(pathArea);
        add(okButton, BorderLayout.SOUTH);
        addChooseButtonClickListener();
        addOkButtonClickListener();
    }

    private void setFileChooseConfig() {
        fileChooser.setFileSelectionMode(DIRECTORIES_ONLY);
        fileChooser.setMultiSelectionEnabled(true);
    }

    @Override
    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    @Override
    public JTextArea getPathArea() {
        return pathArea;
    }

    private void addChooseButtonClickListener() {
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenter.onPathsChosen();
            }
        });
    }

    private void addOkButtonClickListener() {
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenter.onAccept();
            }
        });
    }

}
