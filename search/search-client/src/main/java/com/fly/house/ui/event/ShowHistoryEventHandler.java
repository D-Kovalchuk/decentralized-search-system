package com.fly.house.ui.event;

import com.fly.house.ui.presenter.HistoryPresenter;
import com.fly.house.ui.qualifier.Handler;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dimon on 3/7/14.
 */
@Handler
public class ShowHistoryEventHandler {

    private static Logger logger = LoggerFactory.getLogger(ShowHistoryEventHandler.class);

    @Autowired
    private HistoryPresenter presenter;

    @Subscribe
    public void onHistoryShow(ShowHistoryEvent event) {
        logger.debug("handling {}", event.getClass().getName());
        presenter.go();
    }
}
