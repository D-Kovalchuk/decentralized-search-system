package com.fly.house.core.exception;

/**
 * Created by dimon on 2/27/14.
 */
public class CookieNotFoundException extends RestException {

    public CookieNotFoundException() {
        super();
    }

    public CookieNotFoundException(String message) {
        super(message);
    }

    public CookieNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CookieNotFoundException(Throwable cause) {
        super(cause);
    }
}
