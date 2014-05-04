package com.fly.house.dao.repository;

import com.fly.house.model.Path;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dimon on 5/2/14.
 */
@Repository
public interface PathRepository extends JpaRepository<Path, Long> {

    @Query("select p from Path p where p.account.name = :name")
    List<Path> findByAccountName(@Param("name") String name);

}
