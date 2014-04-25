package com.fly.house.dao.service.security.registration;

import com.fly.house.dao.repository.AccountRepository;
import com.fly.house.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dimon on 4/25/14.
 */
@Service
public class RegistrationService {

    private AccountRepository repository;

    private BCryptPasswordEncoder encoder;

    @Autowired
    public RegistrationService(AccountRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public Account register(Account account) {
        String password = account.getPassword();
        String securedPassword = encoder.encode(password);
        account.setPassword(securedPassword);

        return repository.save(account);
    }

    public List<Account> findTop(int n) {
        Sort sort = new Sort(new Sort.Order("artifacts.size"));
        List<Account> accounts = repository.findAll(sort);
        return accounts.subList(0, n);
    }

    public Page<Account> findAllAccounts(int page, int size) {
        Pageable pageable = new PageRequest(page, size);
        return repository.findAll(pageable);
    }


}
