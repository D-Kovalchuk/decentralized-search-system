package com.fly.house.rest.exception;

/**
 * Created by dimon on 3/30/14.
 */
public class RestException extends RuntimeException {

    public RestException(String message) {
        super(message);
    }

    public RestException() {
        super();
    }

    public RestException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestException(Throwable cause) {
        super(cause);
    }
}
