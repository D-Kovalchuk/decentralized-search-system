package com.fly.house.authentication;

import com.fly.house.core.event.SystemLogoutEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by dimon on 05.05.14.
 */
@Component
public class AccountRepositoryLogoutEventHandler implements ApplicationListener<SystemLogoutEvent> {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void onApplicationEvent(SystemLogoutEvent systemLogoutEvent) {
        accountRepository.remove();
    }
}
