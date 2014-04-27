package com.fly.house.registration.service;

import com.fly.house.dao.repository.AccountRepository;
import com.fly.house.model.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.fly.house.model.Role.ROLE_USER;
import static java.util.Arrays.asList;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by dimon on 4/25/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    public static final String ENCODED_PASSWORD = "encodedPassword";
    public static final String PURE_PASSWORD = "n123456n";

    @Mock
    private AccountRepository repository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void registerShouldEncodePassword() throws Exception {
        Account account = createAccount();
        when(encoder.encode(PURE_PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(repository.save(account)).thenReturn(account);

        Account savedAccount = accountService.register(account);

        verify(encoder).encode(PURE_PASSWORD);
        assertThat(savedAccount.getPassword(), equalTo(ENCODED_PASSWORD));
    }

    @Test
    public void registerShouldSetRoleUserAsDefault() {
        Account account = createAccount();
        when(repository.save(account)).thenReturn(account);

        Account savedAccount = accountService.register(account);

        assertThat(savedAccount.getRole(), equalTo(ROLE_USER));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void findTopShouldThrowExceptionWhenThereIsLessThanRequestedAccounts() throws Exception {
        when(repository.findAll(any(Sort.class))).thenReturn(asList(createAccount()));

        accountService.findTop(2);
    }

    @Test
    public void findTopShouldReturnListOfAccountsWhenThereIsEnoughAccountsInDB() throws Exception {
        when(repository.findTop()).thenReturn(asList(createAccount(), createAccount()));

        accountService.findTop(2);
    }

    private Account createAccount() {
        Account account = new Account();
        account.setPassword(PURE_PASSWORD);
        account.setName("login");
        account.setEmail("d@admin.com");
        return account;
    }

}
