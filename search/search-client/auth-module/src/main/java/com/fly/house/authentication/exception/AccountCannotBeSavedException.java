package com.fly.house.authentication.exception;

/**
 * Created by dimon on 05.05.14.
 */
public class AccountCannotBeSavedException extends AuthorizationException {
    public AccountCannotBeSavedException() {
        super();
    }

    public AccountCannotBeSavedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountCannotBeSavedException(String message) {
        super(message);
    }

    public AccountCannotBeSavedException(Throwable cause) {
        super(cause);
    }
}
