package com.fly.house.ui.presenter.api;

import com.fly.house.ui.view.ViewContainer;
import com.fly.house.ui.view.api.View;
import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * Created by dimon on 3/9/14.
 */
public abstract class AbstractPresenter<V extends View> implements Presenter {

    protected ViewContainer container;
    protected V view;
    protected EventBus eventBus;
    private static Logger logger = LoggerFactory.getLogger(AbstractPresenter.class);

    protected AbstractPresenter(EventBus eventBus, V view, ViewContainer container) {
        this.eventBus = eventBus;
        this.view = view;
        this.container = container;
        view.setPresenter(this);
    }

    @Override
    public void init() {
        logger.debug("Start initializing {}", getClass().getName());
    }

    @Override
    public void go() {
        init();
        logger.debug("Clean container");
        container.removeAll();
        view.getTopMenu().setVisible(true);
        logger.debug("Add view to container");
        view.cleanUp();
        container.add(view.asJPanel(), BorderLayout.CENTER);
        logger.debug("Update ui");
        container.updateUI();
    }
}
