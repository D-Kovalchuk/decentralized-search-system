package com.fly.house.ui.event;

import com.fly.house.ui.presenter.PathsPresenter;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dimon on 3/7/14.
 */
@Component
public class ShowPathsEventHandler {

    @Autowired
    private PathsPresenter presenter;

    @Subscribe
    public void onShowPaths(ShowPathsEvent event) {
        presenter.go();
    }
}
