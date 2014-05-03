package com.fly.house.download.event;

import com.fly.house.core.dto.PathPackage;
import com.fly.house.model.Account;
import com.fly.house.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

/**
 * Created by dimon on 03.05.14.
 */
@Component
public class FileDownloadErrorEventHandler implements ApplicationListener<FileDownloadErrorEvent> {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private BlockingQueue<CompletableFuture<Optional<File>>> queue;


    @Override
    public void onApplicationEvent(FileDownloadErrorEvent event) {
        File file = (File) event.getSource();
        Account account = file.getAccount();
        String name = account.getName();
        String path = file.getPath();
        //todo fix hardcoded port
        PathPackage pathPackage = new PathPackage(name, path, 8080);
        jmsTemplate.convertAndSend(pathPackage);
        queue.remove(queue);
    }
}
