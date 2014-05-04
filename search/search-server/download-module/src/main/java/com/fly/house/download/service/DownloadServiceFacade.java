package com.fly.house.download.service;

import com.fly.house.core.dto.PathPackage;
import com.fly.house.download.model.DownloadInfo;
import com.fly.house.model.Account;
import com.fly.house.model.File;
import com.fly.house.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Created by dimon on 03.05.14.
 */
@Service
public class DownloadServiceFacade {

    private MessagingService messagingService;

    private FutureBuffer futureBuffer;

    private DownloadTaskExecutor downloadTaskExecutor;

    private FileService fileService;

    @Autowired
    public DownloadServiceFacade(MessagingService messagingService,
                                 FutureBuffer futureBuffer,
                                 DownloadTaskExecutor downloadTaskExecutor,
                                 FileService fileService) {
        this.messagingService = messagingService;
        this.futureBuffer = futureBuffer;
        this.downloadTaskExecutor = downloadTaskExecutor;
        this.fileService = fileService;
    }

    public void send(File file) {
        File savedFile = fileService.save(file);
        futureBuffer.remove(savedFile);
        messagingService.sendNext(savedFile);
    }

    public void receive() throws InterruptedException {
        DownloadInfo downloadInfo = messagingService.receiveAndConvert();
        CompletableFuture<Optional<File>> future = downloadTaskExecutor.process(downloadInfo);
        futureBuffer.add(future);
    }

    public void sendBack(File file) {
        Account account = file.getAccount();
        String name = account.getName();
        String path = file.getPath();
        //todo fix hardcoded port
        PathPackage pathPackage = new PathPackage(name, path, 8080);
        messagingService.sendBack(pathPackage);
        futureBuffer.remove(file);
    }


    //fixme
    //throws DataAccessException
    //it must be not here
}
