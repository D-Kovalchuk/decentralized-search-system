package com.fly.house.download.service;

import com.fly.house.model.File;
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

    public void add(CompletableFuture<Optional<File>> future) throws InterruptedException {
        queue.put(future);
    }

    public void remove(File file) {
        boolean remove = queue.removeIf(future -> isCompleted(future) && isEqual(future, file));
        if (!remove) {
            delayRemoval(file);
        }
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
        try {
            TimeUnit.SECONDS.sleep(DELAY_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        queue.removeIf(future -> isCompleted(future) && isEqual(future, file));
    }
}
