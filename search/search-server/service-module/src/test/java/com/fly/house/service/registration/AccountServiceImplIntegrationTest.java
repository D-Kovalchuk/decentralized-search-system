package com.fly.house.service.registration;


import com.fly.house.dao.repository.AccountRepository;
import com.fly.house.dao.test.AbstractDbUnit;
import com.fly.house.model.Account;
import com.fly.house.model.Artifact;
import com.fly.house.service.config.RegistrationConfig;
import com.fly.house.service.exception.RegistrationException;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.List;

import static com.fly.house.model.Role.ROLE_USER;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

/**
 * Created by dimon on 4/26/14.
 */
@Transactional
@ActiveProfiles("dev")
@ContextConfiguration(classes = RegistrationConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountServiceImplIntegrationTest extends AbstractDbUnit {

    public static final String PURE_PASSWORD = "n123456n";
    public static final String DEFAULT_LOGIN = "login";
    public static final String DEFAULT_EMAIL = "d@admin.com";

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private Environment env;

    @Autowired
    private DataSource dataSource;

    @Before
    public void setUp() throws Exception {
        loadDataSet(env);
        cleanAndInsert(dataSource);
    }

    @Test
    public void ormMappingShouldWork() throws Exception {
        Account account = createAccount();

        accountService.register(account);

        repository.flush();
    }

    @Test
    public void registrationShouldSaveAccount() throws Exception {
        Account account = createAccount();

        Account savedAccount = accountService.register(account);

        assertNotNull(savedAccount.getId());
    }

    @Test
    public void accountShouldHaveUserRoleAfterRegistration() throws Exception {
        Account account = createAccount();

        Account savedAccount = accountService.register(account);

        assertThat(savedAccount.getRole(), is(ROLE_USER));
    }


    @Test(expected = RegistrationException.class)
    public void registrationShouldDenyDuplicationOfEmails() throws Exception {
        Account account = createAccount();
        accountService.register(account);

        repository.flush();

        Account duplicatedAccount = new Account("anotherLogin", DEFAULT_EMAIL, PURE_PASSWORD);
        accountService.register(duplicatedAccount);
    }

    @Test(expected = RegistrationException.class)
    public void registrationShouldDenyDuplicationOfLogins() throws Exception {
        Account account = createAccount();
        accountService.register(account);

        repository.flush();

        Account duplicatedAccount = new Account(DEFAULT_LOGIN, "anotherEmail@admin.com", PURE_PASSWORD);
        accountService.register(duplicatedAccount);
    }

    @Test
    public void registrationShouldSaveAccountWithEncodedPassword() throws Exception {
        Account account = createAccount();

        Account savedAccount = accountService.register(account);

        assertThat(savedAccount.getPassword(), is(not(equalTo(PURE_PASSWORD))));
    }

    @Test
    public void findAccountById() throws Exception {
        Account account = accountService.findAccountById(1L);

        assertNotNull(account.getId());
    }

    @Test
    public void findAccountByIdShouldReturnNullWhenAccountDoesNotExist() throws Exception {
        Account account = accountService.findAccountById(-1L);

        assertNull(account);
    }

    @Test
    public void findAllAccountsShouldReturn4Pages() throws Exception {
        Page<Account> page = accountService.findAllAccounts(1, 5);

        assertThat(page.getTotalPages(), equalTo(4));
    }

    @Test
    public void findAllAccountsShouldHave16Records() throws Exception {
        Page<Account> page = accountService.findAllAccounts(1, 5);

        assertThat(page.getTotalElements(), Matchers.equalTo(16L));
    }

    @Test
    public void findTopShouldReturnAccountsWithBiggestAmountOfArtifactsFirst() throws Exception {
        List<Account> top = accountService.findTop(2);

        Account firstAccount = top.get(0);
        List<Artifact> artifacts = firstAccount.getArtifacts();
        assertThat(artifacts.size(), Matchers.equalTo(2));
    }

    @Test
    public void findTopShouldLimitOutcomeAccountsAmount() throws Exception {
        List<Account> top = accountService.findTop(5);

        assertThat(top.size(), Matchers.equalTo(5));
    }

    @Test
    public void findAccountByName() throws Exception {
        Account account = accountService.findAccountByName("login1");

        assertNotNull(account);
    }

    private Account createAccount() {
        return new Account(DEFAULT_LOGIN, DEFAULT_EMAIL, PURE_PASSWORD);
    }
}
