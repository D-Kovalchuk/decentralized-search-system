package com.fly.house.io.exceptions;

/**
 * Created by dimon on 1/29/14.
 */
public class WatchServiceRegistrationException extends WatchServiceException {


    public WatchServiceRegistrationException() {
        super();
    }

    public WatchServiceRegistrationException(String message) {
        super(message);
    }

    public WatchServiceRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public WatchServiceRegistrationException(Throwable cause) {
        super(cause);
    }
}
