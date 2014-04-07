package com.fly.house.io;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dimon on 3/4/14.
 */
@Component
public class Initializer implements InitializingBean {

    @Autowired
    private InMemoryWSStorage storage;

    @Autowired
    private WatchServiceExecutor watchServiceExecutor;

    @Override
    public void afterPropertiesSet() throws Exception {
        storage.load();
        watchServiceExecutor.init();
    }


}
