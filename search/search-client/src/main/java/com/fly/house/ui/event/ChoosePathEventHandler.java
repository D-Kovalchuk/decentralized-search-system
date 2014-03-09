package com.fly.house.ui.event;

import com.fly.house.ui.presenter.ChoosePathPresenter;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dimon on 3/7/14.
 */
@Component
public class ChoosePathEventHandler {

    @Autowired
    private ChoosePathPresenter presenter;

    @Subscribe
    public void onChoosPath(ChoosePathEvent event) {
        presenter.go();
    }

}
