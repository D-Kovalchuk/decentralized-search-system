package com.fly.house.dao.repository;

import com.fly.house.model.Account;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dimon on 4/22/14.
 */
@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {

    List<Account> findAll();

    List<Account> findAll(Sort sort);
}
