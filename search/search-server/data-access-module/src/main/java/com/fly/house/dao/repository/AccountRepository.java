package com.fly.house.dao.repository;

import com.fly.house.model.Account;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dimon on 4/22/14.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAll();

    List<Account> findAll(Sort sort);

    @Query("select a from Account a order by a.artifacts.size desc")
    List<Account> findTop();

    Account findByName(String name);
}
