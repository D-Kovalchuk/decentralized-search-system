package com.fly.house.io.exceptions;

/**
 * Created by dimon on 1/28/14.
 */
public class NotDirectoryException extends PathRepositoryException {

    public NotDirectoryException() {
        super();
    }

    public NotDirectoryException(String message) {
        super(message);
    }

    public NotDirectoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotDirectoryException(Throwable cause) {
        super(cause);
    }
}
