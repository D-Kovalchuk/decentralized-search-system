package com.fly.house.ui.event;

import com.fly.house.core.event.SystemLoginEvent;
import com.fly.house.ui.presenter.ChoosePathPresenter;
import com.fly.house.ui.qualifier.Handler;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dimon on 3/7/14.
 */
@Handler
public class ChoosePathEventHandler {

    private static Logger logger = LoggerFactory.getLogger(ChoosePathEventHandler.class);

    @Autowired
    private ChoosePathPresenter presenter;

    @Autowired
    private SystemEventPublisher publisher;

    @Subscribe
    public void onChoosPath(ChoosePathEvent event) {
        logger.debug("handling {}", event.getClass().getName());
        publisher.publish(new SystemLoginEvent("login"));
        presenter.go();
    }

}
