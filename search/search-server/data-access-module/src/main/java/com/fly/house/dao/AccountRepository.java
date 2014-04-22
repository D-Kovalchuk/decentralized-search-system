package com.fly.house.dao;

import com.fly.house.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dimon on 4/22/14.
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    List<Account> findAll();

}
