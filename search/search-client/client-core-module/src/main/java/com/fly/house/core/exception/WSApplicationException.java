package com.fly.house.core.exception;

/**
 * Created by dimon on 4/13/14.
 */
public class WSApplicationException extends RuntimeException {

    public WSApplicationException(String message) {
        super(message);
    }

    public WSApplicationException() {
        super();
    }

    public WSApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public WSApplicationException(Throwable cause) {
        super(cause);
    }

}
