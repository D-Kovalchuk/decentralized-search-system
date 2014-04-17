package com.fly.house.authentication;

import com.fly.house.core.profile.Dev;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by dimon on 4/8/14.
 */
@Service
@Dev
public class FakeAuthenticationService implements AuthenticationService {

    private boolean authorized;

    private static Logger logger = LoggerFactory.getLogger(FakeAuthenticationService.class);

    public FakeAuthenticationService() {
        authorized = false;
    }

    public FakeAuthenticationService(boolean authorized) {
        logger.debug("is auth {}", authorized);
        this.authorized = authorized;
    }

    @Override
    public void authentication(String login, String password) {
        logger.debug("logged in");
        authorized = true;
    }

    @Override
    public void logout() {
        logger.debug("logged out");
        authorized = false;
    }

    @Override
    public boolean isAuthorized() {
        return authorized;
    }
}
