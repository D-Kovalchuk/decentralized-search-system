package com.fly.house.authentication.exception;

import com.fly.house.io.exceptions.WatchServiceException;

/**
 * Created by dimon on 2/27/14.
 */
public class CookieNotFoundException extends WatchServiceException {

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
