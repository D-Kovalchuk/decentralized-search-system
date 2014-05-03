package com.fly.house.download.service;

import com.fly.house.core.event.SystemEventPublisher;
import com.fly.house.download.event.FileDownloadCompleteEvent;
import com.fly.house.download.event.FileDownloadErrorEvent;
import com.fly.house.download.model.DownloadInfo;
import com.fly.house.model.Account;
import com.fly.house.model.Artifact;
import com.fly.house.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static java.util.Objects.nonNull;

/**
 * Created by dimon on 03.05.14.
 */
@Service
public class DownloadTaskExecutor {

    @Autowired
    private ThreadPoolExecutor executor;

    @Autowired
    private SystemEventPublisher publisher;

    public CompletableFuture<Optional<File>> process(DownloadInfo downloadInfo) {
        return CompletableFuture
                .supplyAsync(getFileSupplier(downloadInfo), executor)
                .handle(handler());
    }

    private Supplier<File> getFileSupplier(DownloadInfo downloadInfo) {
        return () -> {
            DownloadManager downloadManager = new DownloadManager(downloadInfo);
            Artifact artifact = downloadManager.start();
            String path = downloadInfo.getPath();
            Account account = downloadInfo.getAccount();
            return new File(account, artifact, path);
        };
    }

    private BiFunction<File, Throwable, Optional<File>> handler() {
        return (file, ex) -> {
            if (nonNull(ex)) {
                publisher.publish(new FileDownloadErrorEvent(file, ex));
                return Optional.of(file);
            } else {
                publisher.publish(new FileDownloadCompleteEvent(file));
                return Optional.of(file);
            }
        };
    }
}
