package com.fly.house.download.service;

import com.fly.house.core.dto.PathPackage;
import com.fly.house.download.model.Converter;
import com.fly.house.download.model.DownloadInfo;
import com.fly.house.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

/**
 * Created by dimon on 03.05.14.
 */
@Component
public class PathInfoMessageListener {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private DownloadTaskExecutor downloadTaskExecutor;

    @Autowired
    private Converter converter;

    @Autowired
    private BlockingQueue<CompletableFuture<Optional<File>>> queue;

    public void startRecieving() {
        while (true) {
            try {
                CompletableFuture<Optional<File>> future = receive();
                queue.put(future);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private CompletableFuture<Optional<File>> receive() throws InterruptedException {
        PathPackage pathPackage = (PathPackage) jmsTemplate.receiveAndConvert();
        DownloadInfo downloadInfo = converter.convert(pathPackage);
        return downloadTaskExecutor.process(downloadInfo);
    }


}
