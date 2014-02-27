package com.fly.house.io.exceptions;

/**
 * Created by dimon on 2/27/14.
 */
public class SnapshotIOException extends WatchServiceException {
    public SnapshotIOException() {
        super();
    }

    public SnapshotIOException(String message) {
        super(message);
    }

    public SnapshotIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public SnapshotIOException(Throwable cause) {
        super(cause);
    }
}
