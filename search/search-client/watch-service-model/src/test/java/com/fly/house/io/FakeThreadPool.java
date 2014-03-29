package com.fly.house.io;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Future;

/**
 * Created by dimon on 3/12/14.
 */
public class FakeThreadPool extends ThreadPoolTaskExecutor {
    @Override
    public Future<?> submit(Runnable task) {
        PathWatchService pathWatchService = (PathWatchService) task;
        pathWatchService.stop();
        task.run();
        return null;
    }
}
