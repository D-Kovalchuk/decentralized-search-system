package com.fly.house.core.exception;

/**
 * Created by dimon on 2/26/14.
 */
public class UnauthorizedException extends RestException {

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }

}
