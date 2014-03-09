package com.fly.house.ui.event;

import com.fly.house.ui.presenter.HistoryPresenter;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dimon on 3/7/14.
 */
@Component
public class ShowHistoryEventHandler {
    @Autowired
    private HistoryPresenter presenter;

    @Subscribe
    public void onHistoryShow(ShowHistoryEvent event) {
        presenter.go();
    }
}
