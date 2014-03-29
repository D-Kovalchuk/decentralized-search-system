package com.fly.house.io.exceptions;

/**
 * Created by dimon on 1/28/14.
 */
public class WatchServiceException extends RuntimeException {

    public WatchServiceException() {
        super();
    }

    public WatchServiceException(String message) {
        super(message);
    }

    public WatchServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public WatchServiceException(Throwable cause) {
        super(cause);
    }
}
