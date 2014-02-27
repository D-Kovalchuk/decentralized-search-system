package com.fly.house.authentication.exception;

import com.fly.house.io.exceptions.WatchServiceException;

/**
 * Created by dimon on 2/27/14.
 */
public class CookieIOException extends WatchServiceException {

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
