package com.fly.house.ui.view;

import com.fly.house.ui.event.ChoosePathEvent;
import com.fly.house.ui.event.LogoutEvent;
import com.fly.house.ui.event.ShowHistoryEvent;
import com.fly.house.ui.event.ShowPathsEvent;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by dimon on 3/7/14.
 */
@Component
public class TopMenu {

    @Autowired
    private EventBus eventBus;
    private JMenuBar menuBar = new JMenuBar();

    @PostConstruct
    public void init() {
        addPathsMenu();
        addHistoryMenu();
        addLogoutMenu();
    }

    private void addLogoutMenu() {
        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(new Dispatcher(new LogoutEvent()));
        menuBar.add(logoutItem);
    }

    private void addHistoryMenu() {
        JMenu history = new JMenu("History");
        JMenuItem historyItem = new JMenuItem("Show history");
        historyItem.addActionListener(new Dispatcher(new ShowHistoryEvent()));
        history.add(historyItem);
        menuBar.add(history);
    }

    private void addPathsMenu() {
        JMenu paths = new JMenu("Paths");
        JMenuItem chooseItem = new JMenuItem("Choose path");
        chooseItem.addActionListener(new Dispatcher(new ChoosePathEvent()));
        paths.add(chooseItem);
        JMenuItem showPathItem = new JMenuItem("Show paths");
        showPathItem.addActionListener(new Dispatcher(new ShowPathsEvent()));
        paths.add(showPathItem);
        menuBar.add(paths);
    }

    public void setVisible(boolean visible) {
        menuBar.setVisible(visible);
    }

    public JMenuBar asMenuBar() {
        return menuBar;
    }

    private class Dispatcher implements ActionListener {

        private Object event;

        private Dispatcher(Object event) {
            this.event = event;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            eventBus.post(event);
        }
    }
}
