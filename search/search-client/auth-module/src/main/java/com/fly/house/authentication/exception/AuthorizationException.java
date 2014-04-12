package com.fly.house.authentication.exception;

import com.fly.house.core.exception.WSApplicationException;

/**
 * Created by dimon on 3/28/14.
 */
public class AuthorizationException extends WSApplicationException {

    public AuthorizationException() {
        super();
    }

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationException(Throwable cause) {
        super(cause);

    }
}