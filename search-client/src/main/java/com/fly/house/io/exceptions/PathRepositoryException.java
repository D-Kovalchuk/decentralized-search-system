package com.fly.house.io.exceptions;

/**
 * Created by dimon on 1/29/14.
 */
public class PathRepositoryException extends WatchServiceException {

    public PathRepositoryException(String message) {
        super(message);
    }

    public PathRepositoryException() {
        super();
    }

    public PathRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public PathRepositoryException(Throwable cause) {
        super(cause);
    }
}
