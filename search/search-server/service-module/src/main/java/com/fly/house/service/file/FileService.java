package com.fly.house.service.file;

import com.fly.house.model.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Created by dimon on 04.05.14.
 */
public interface FileService {

    File save(File file);

    void delete(Long id);

    void update(File file);

    Page<File> findAllFiles(Pageable pageable);

    File findFileById(Long id);

}
