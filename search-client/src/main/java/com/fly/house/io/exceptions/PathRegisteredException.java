package com.fly.house.io.exceptions;

/**
 * Created by dimon on 1/28/14.
 */
public class PathRegisteredException extends PathRepositoryException {

    public PathRegisteredException() {
        super();
    }

    public PathRegisteredException(String message) {
        super(message);
    }

    public PathRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public PathRegisteredException(Throwable cause) {
        super(cause);
    }
}
