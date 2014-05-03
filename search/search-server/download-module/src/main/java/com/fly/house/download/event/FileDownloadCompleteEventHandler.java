package com.fly.house.download.event;

import com.fly.house.dao.repository.ArtifactRepository;
import com.fly.house.dao.repository.FileRepository;
import com.fly.house.model.Artifact;
import com.fly.house.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

/**
 * Created by dimon on 03.05.14.
 */
@Component
public class FileDownloadCompleteEventHandler implements ApplicationListener<FileDownloadCompleteEvent> {

    @Autowired
    private ArtifactRepository artifactRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private BlockingQueue<CompletableFuture<Optional<File>>> queue;

    //todo send id of saved file to another jms disttination
    @Override
    public void onApplicationEvent(FileDownloadCompleteEvent event) {
        File file = (File) event.getSource();
        File savedFile = save(file);
        queue.remove(savedFile);
    }

    @Transactional
    public File save(File file) {
        //todo find and set account into file
        //Account account = accountService.findAccountByName(name);
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
