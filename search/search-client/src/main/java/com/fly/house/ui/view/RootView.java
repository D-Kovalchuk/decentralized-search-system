package com.fly.house.ui.view;

import com.fly.house.authentication.Authorization;
import com.fly.house.ui.event.LoginEvent;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dimon on 3/6/14.
 */
@Component
public class RootView extends JFrame {

    @Autowired
    private Authorization authorization;

    @Autowired
    private EventBus eventBus;

    @Autowired
    private JPanel container;

    public void run() {
        setPreferredSize(new Dimension(500, 500));

        if (authorization.isAuthorized()) {
            //fire choose paths event
        } else {
            eventBus.post(new LoginEvent());
        }

        container.setPreferredSize(new Dimension(500, 500));
        add(container);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
    }

}
