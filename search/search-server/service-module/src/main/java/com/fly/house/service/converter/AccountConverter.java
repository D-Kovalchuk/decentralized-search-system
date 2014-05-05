package com.fly.house.service.converter;

import com.fly.house.core.dto.AccountDto;
import com.fly.house.model.Account;

/**
 * Created by dimon on 04.05.14.
 */
public class AccountConverter implements Converter<Account, AccountDto> {

    @Override
    public AccountDto convert(Account account) {
        return new AccountDto(
                account.getId(),
                account.getName(),
                account.getPassword(),
                account.getEmail());
    }

}
