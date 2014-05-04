package com.fly.house.download.service;

import com.fly.house.core.dto.PathPackage;
import com.fly.house.dao.repository.ArtifactRepository;
import com.fly.house.dao.repository.FileRepository;
import com.fly.house.download.model.DownloadInfo;
import com.fly.house.model.Account;
import com.fly.house.model.Artifact;
import com.fly.house.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Created by dimon on 03.05.14.
 */
@Service
public class DownloadServiceFacade {

    private ArtifactRepository artifactRepository;

    private FileRepository fileRepository;

    private MessagingService messagingService;

    private FutureBuffer futureBuffer;

    private DownloadTaskExecutor downloadTaskExecutor;

    @Autowired
    public DownloadServiceFacade(ArtifactRepository artifactRepository,
                                 FileRepository fileRepository,
                                 MessagingService messagingService,
                                 FutureBuffer futureBuffer,
                                 DownloadTaskExecutor downloadTaskExecutor) {
        this.artifactRepository = artifactRepository;
        this.fileRepository = fileRepository;
        this.messagingService = messagingService;
        this.futureBuffer = futureBuffer;
        this.downloadTaskExecutor = downloadTaskExecutor;
    }

    public void send(File file) {
        File savedFile = save(file);
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
    @Transactional
    private File save(File file) {
        Artifact artifact = file.getArtifact();
        String digest = artifact.getDigest();
//        String digest = generateDigest(artifact);
        Artifact presentArtifact = artifactRepository.findByDigest(digest);
        if (presentArtifact != null) {
            file.setArtifact(presentArtifact);
        } else {
            artifact.setDigest(digest);
        }
        return fileRepository.save(file);
    }

//    private String generateDigest(Artifact artifact) {
//        String content = artifact.getFullText();
//        return DigestUtils.md5Hex(content);
//    }
}
