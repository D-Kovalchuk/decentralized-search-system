package com.fly.house.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dimon on 3/4/14.
 */
@Component
public class WSInitializer {

    @Autowired
    private InMemoryWSStorage storage;

    @Autowired
    private WatchServiceExecutor watchServiceExecutor;

    public void initialize() {
        storage.load();
        watchServiceExecutor.init();
    }


}
