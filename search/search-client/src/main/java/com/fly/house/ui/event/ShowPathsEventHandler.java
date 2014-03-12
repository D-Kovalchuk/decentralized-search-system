package com.fly.house.ui.event;

import com.fly.house.ui.presenter.PathsPresenter;
import com.fly.house.ui.qualifier.Handler;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dimon on 3/7/14.
 */
@Handler
public class ShowPathsEventHandler {

    @Autowired
    private PathsPresenter presenter;

    @Subscribe
    public void onShowPaths(ShowPathsEvent event) {
        presenter.go();
    }
}
