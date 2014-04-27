package com.fly.house.core.exception;

/**
 * Created by dimon on 4/26/14.
 */
public class BusinessException extends WSApplicationException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException() {
        super();
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }
}
