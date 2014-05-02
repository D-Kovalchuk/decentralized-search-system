package com.fly.house.dao.repository;

import com.fly.house.model.Path;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by dimon on 5/2/14.
 */
@Repository
public interface PathRepository extends JpaRepository<Path, Long> {

}
