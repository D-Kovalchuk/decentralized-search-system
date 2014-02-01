package com.fly.house.io.exceptions;

/**
 * Created by dimon on 1/28/14.
 */
public class DirectoryNotFoundException extends PathRepositoryException {

    public DirectoryNotFoundException() {
        super();
    }

    public DirectoryNotFoundException(String message) {
        super(message);
    }

    public DirectoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DirectoryNotFoundException(Throwable cause) {
        super(cause);
    }
}
