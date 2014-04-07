package com.fly.house.authentication;

/**
 * Created by dimon on 4/7/14.
 */
public interface AuthenticationService {

    void authentication(String login, String password);

    void logout();

    boolean isAuthorized();

}