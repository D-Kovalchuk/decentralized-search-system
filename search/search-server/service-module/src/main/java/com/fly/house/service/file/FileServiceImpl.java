package com.fly.house.service.file;

import com.fly.house.dao.repository.ArtifactRepository;
import com.fly.house.dao.repository.FileRepository;
import com.fly.house.model.Artifact;
import com.fly.house.model.File;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dimon on 04.05.14.
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {

    private ArtifactRepository artifactRepository;

    private FileRepository fileRepository;

    @Autowired
    public FileServiceImpl(ArtifactRepository artifactRepository, FileRepository fileRepository) {
        this.artifactRepository = artifactRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public File save(File file) {
        Artifact artifact = file.getArtifact();
        String digest = generateDigest(artifact);
        Artifact presentArtifact = artifactRepository.findByDigest(digest);
        if (presentArtifact != null) {
            file.setArtifact(presentArtifact);
        } else {
            artifact.setDigest(digest);
        }
        return fileRepository.save(file);
    }

    @Override
    public void delete(Long id) {
        fileRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = false)
    public File findFileById(Long id) {
        return fileRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = false)
    public Page<File> findAllFiles(Pageable pageable) {
        return fileRepository.findAll(pageable);
    }

    @Override
    public void update(File file) {
        //todo
    }

    private String generateDigest(Artifact artifact) {
        String content = artifact.getFullText();
        return DigestUtils.sha1Hex(content);
    }
}
