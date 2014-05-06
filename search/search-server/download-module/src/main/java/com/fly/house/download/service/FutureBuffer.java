package com.fly.house.download.service;

import com.fly.house.model.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by dimon on 03.05.14.
 */
@Service
public class FutureBuffer {

    @Autowired
    private BlockingQueue<CompletableFuture<Optional<File>>> queue;

    private static final int DELAY_TIME = 2;

    private static Logger logger = LoggerFactory.getLogger(FutureBuffer.class);

    public void add(CompletableFuture<Optional<File>> future) throws InterruptedException {
        logger.debug("{} has been added to the queue", future);
        queue.put(future);
    }

    public void remove(File file) {
        boolean remove = removeIfComplated(file);
        logger.debug("Remove ({}) file {}", remove, file);
        if (!remove) {
            delayRemoval(file);
        }
    }

    private boolean removeIfComplated(File file) {
        return queue.removeIf(future -> isCompleted(future) && isEqual(future, file));
    }

    private boolean isCompleted(CompletableFuture<Optional<File>> future) {
        return (future.isDone() || future.isCompletedExceptionally());
    }

    private boolean isEqual(CompletableFuture<Optional<File>> future, File file) {
        try {
            Optional<File> optional = future.get();
            File savedFile = optional.get();
            return file.equals(savedFile);
        } catch (InterruptedException | ExecutionException e) {
            //NOP
        }
        return false;
    }

    private void delayRemoval(File file) {
        logger.debug("Delay removal. Delay time = {}", DELAY_TIME);
        try {
            TimeUnit.SECONDS.sleep(DELAY_TIME);
        } catch (InterruptedException e) {
            logger.warn("Exception occurred", e);
        }
        removeIfComplated(file);
    }
}
