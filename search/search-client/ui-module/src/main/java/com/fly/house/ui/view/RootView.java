package com.fly.house.ui.view;

import com.fly.house.ui.event.LoginEvent;
import com.fly.house.ui.qualifier.View;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dimon on 3/6/14.
 */
@View
public class RootView extends JFrame {

    @Autowired
    private EventBus eventBus;

    @Autowired
    private ViewContainer container;

    @Autowired
    private TopMenu menu;

    public void run() {
        setConfig();
        setJMenuBar(menu.asMenuBar());
        addContainer();
        eventBus.post(new LoginEvent());
        pack();
        setVisible(true);
    }

    private void addContainer() {
        container.setPreferredSize(new Dimension(500, 500));
        add(container);
    }

    private void setConfig() {
        setPreferredSize(new Dimension(500, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

}
