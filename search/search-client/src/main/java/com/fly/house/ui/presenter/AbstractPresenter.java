package com.fly.house.ui.presenter;

import com.fly.house.ui.view.TopMenu;
import com.fly.house.ui.view.View;
import com.fly.house.ui.view.ViewContainer;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;

/**
 * Created by dimon on 3/9/14.
 */
public abstract class AbstractPresenter<V extends View> implements Presenter {

    @Autowired
    protected TopMenu menu;
    protected ViewContainer container;
    protected V view;
    protected EventBus eventBus;

    protected AbstractPresenter(EventBus eventBus, V view, ViewContainer container) {
        this.eventBus = eventBus;
        this.view = view;
        this.container = container;
        view.setPresenter(this);
    }

    @Override
    public void go() {
        container.removeAll();
        menu.setVisible(true);
        container.add(view.asJPanel(), BorderLayout.CENTER);
        container.updateUI();
    }
}
