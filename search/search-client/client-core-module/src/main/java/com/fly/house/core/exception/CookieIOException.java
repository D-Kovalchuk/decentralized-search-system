package com.fly.house.core.exception;

/**
 * Created by dimon on 2/27/14.
 */
public class CookieIOException extends RestException {

    public CookieIOException() {
        super();
    }

    public CookieIOException(String message) {
        super(message);
    }

    public CookieIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public CookieIOException(Throwable cause) {
        super(cause);
    }
}
