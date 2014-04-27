package com.fly.house.registration.service;

import com.fly.house.dao.repository.AccountRepository;
import com.fly.house.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dimon on 4/25/14.
 */
@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private AccountRepository repository;

    private BCryptPasswordEncoder encoder;

    @Autowired
    public AccountServiceImpl(AccountRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public Account register(Account account) {
        String password = account.getPassword();
        String securedPassword = encoder.encode(password);
        account.setPassword(securedPassword);
        return repository.save(account);
    }

    @Override
    public Account findAccountById(Long id) {
        return repository.findOne(id);
    }

    @Override
    public List<Account> findTop(int n) {
        List<Account> accounts = repository.findTop();
        return accounts.subList(0, n);
    }

    @Override
    public Page<Account> findAllAccounts(int page, int size) {
        Pageable pageable = new PageRequest(page, size);
        return repository.findAll(pageable);
    }

}
