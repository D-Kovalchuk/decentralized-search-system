package com.fly.house.download.service;

import com.fly.house.core.dto.PathPackage;
import com.fly.house.download.exception.UserOfflineException;
import com.fly.house.download.model.DownloadInfo;
import com.fly.house.model.File;
import com.fly.house.service.file.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.fly.house.service.converter.ConverterFactory.getFileConverter;

/**
 * Created by dimon on 03.05.14.
 */
@Service
public class DownloadServiceFacade {

    private MessagingService messagingService;

    private FutureBuffer futureBuffer;

    private DownloadTaskExecutor downloadTaskExecutor;

    private FileService fileService;

    private static Logger logger = LoggerFactory.getLogger(DownloadServiceFacade.class);

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
        logger.debug("Sending file to next the queue");
        File savedFile = fileService.save(file);
        futureBuffer.remove(savedFile);
        messagingService.sendNext(savedFile);
    }

    public void receive() throws InterruptedException {
        try {
            DownloadInfo downloadInfo = messagingService.receiveAndConvert();
            CompletableFuture<Optional<File>> future = downloadTaskExecutor.process(downloadInfo);
            futureBuffer.add(future);
        } catch (UserOfflineException ex) {
            logger.warn("Exception occurred", ex);
        }

    }

    public void sendBack(File file) {
        logger.debug("Sending file back to next the queue");
        PathPackage pathPackage = getFileConverter().convert(file);
        messagingService.sendBack(pathPackage);
        futureBuffer.remove(file);
    }

}
