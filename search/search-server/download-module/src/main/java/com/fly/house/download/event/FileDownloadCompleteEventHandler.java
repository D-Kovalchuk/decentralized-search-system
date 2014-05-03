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
import java.util.concurrent.TimeUnit;

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
        remove(savedFile);
    }

    @Transactional
    public File save(File file) {
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

    private void remove(File file) {
        boolean remove = queue.remove(file);
        if (!remove) {
            delayRemoval(file);
        }
    }

    private void delayRemoval(File file) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        queue.remove(file);
    }

//    private String generateDigest(Artifact artifact) {
//        String content = artifact.getFullText();
//        return DigestUtils.md5Hex(content);
//    }

}
