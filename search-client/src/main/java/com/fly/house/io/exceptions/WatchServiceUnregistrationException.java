package com.fly.house.io.exceptions;

/**
 * Created by dimon on 1/29/14.
 */
public class WatchServiceUnregistrationException extends WatchServiceException {

    public WatchServiceUnregistrationException() {
        super();
    }

    public WatchServiceUnregistrationException(String message) {
        super(message);
    }

    public WatchServiceUnregistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public WatchServiceUnregistrationException(Throwable cause) {
        super(cause);
    }
}
