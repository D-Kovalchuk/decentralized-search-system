package com.fly.house.authentication;

/**
 * Created by dimon on 4/8/14.
 */
public class FakeAuthenticationService implements AuthenticationService {

    private boolean authorized;

    public FakeAuthenticationService(boolean authorized) {
        this.authorized = authorized;
    }

    @Override
    public void authentication(String login, String password) {
        authorized = true;
    }

    @Override
    public void logout() {
        authorized = false;
    }

    @Override
    public boolean isAuthorized() {
        return authorized;
    }
}
