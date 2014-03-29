package com.fly.house.ui.view;

import com.fly.house.ui.event.ChoosePathEvent;
import com.fly.house.ui.event.LogoutEvent;
import com.fly.house.ui.event.ShowHistoryEvent;
import com.fly.house.ui.event.ShowPathsEvent;
import com.fly.house.ui.qualifier.View;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.swing.*;

/**
 * Created by dimon on 3/7/14.
 */
@View
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
        logoutItem.addActionListener(e -> eventBus.post(new LogoutEvent()));
        menuBar.add(logoutItem);
    }

    private void addHistoryMenu() {
        JMenu history = new JMenu("History");
        JMenuItem historyItem = new JMenuItem("Show history");
        historyItem.addActionListener(e -> eventBus.post(new ShowHistoryEvent()));
        history.add(historyItem);
        menuBar.add(history);
    }

    private void addPathsMenu() {
        JMenu paths = new JMenu("Paths");
        JMenuItem chooseItem = new JMenuItem("Choose path");
        chooseItem.addActionListener(e -> eventBus.post(new ChoosePathEvent()));
        paths.add(chooseItem);
        JMenuItem showPathItem = new JMenuItem("Show paths");
        showPathItem.addActionListener(e -> eventBus.post(new ShowPathsEvent()));
        paths.add(showPathItem);
        menuBar.add(paths);
    }

    public void setVisible(boolean visible) {
        menuBar.setVisible(visible);
    }

    public JMenuBar asMenuBar() {
        return menuBar;
    }

}
