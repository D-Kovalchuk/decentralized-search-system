package com.fly.house.io.exceptions;

/**
 * Created by dimon on 1/28/14.
 */
public class PathNotRegisteredException extends PathRepositoryException {


    public PathNotRegisteredException() {
        super();
    }

    public PathNotRegisteredException(Throwable cause) {
        super(cause);
    }

    public PathNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public PathNotRegisteredException(String message) {
        super(message);
    }
}
