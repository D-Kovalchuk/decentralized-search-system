package com.fly.house.dao.repository;

import com.fly.house.model.Artifact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by dimon on 4/29/14.
 */
public interface ArtifactRepository {

    Page<Artifact> searchOnlyAvailable(String queryString, Pageable pageable);

    Page<Artifact> search(String queryString, Pageable pageRequest);

    Artifact save(Artifact artifact);

    void update(Artifact artifact);

    void delete(Long id);

    Artifact findOne(Long id);

}
