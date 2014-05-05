package com.fly.house.authentication.exception;

/**
 * Created by dimon on 05.05.14.
 */
public class AccountNotFoundException extends AuthorizationException {

    public AccountNotFoundException() {
        super();
    }

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountNotFoundException(Throwable cause) {
        super(cause);
    }
}
