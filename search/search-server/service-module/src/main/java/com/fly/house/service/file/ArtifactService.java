package com.fly.house.service.file;

import com.fly.house.model.Artifact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by dimon on 04.05.14.
 */
public interface ArtifactService {

    Page<Artifact> searchOnlyAvailable(String queryString, Pageable pageable);

    Page<Artifact> search(String queryString, Pageable pageRequest);

    Artifact save(Artifact artifact);

    void update(Artifact artifact);

    void delete(Long id);

    Artifact findOne(Long id);

    void index(Long id);

    long size();
}
