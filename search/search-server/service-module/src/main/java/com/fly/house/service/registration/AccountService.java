package com.fly.house.service.registration;

import com.fly.house.model.Account;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by dimon on 4/27/14.
 */
public interface AccountService {

    Account register(Account account);

    Account findAccountById(Long id);

    List<Account> findTop(int n);

    Page<Account> findAllAccounts(int page, int size);

    Account findAccountByName(String name);
}
