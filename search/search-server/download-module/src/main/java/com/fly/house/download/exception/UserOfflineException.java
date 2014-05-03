package com.fly.house.download.exception;

import com.fly.house.core.exception.BusinessException;

/**
 * Created by dimon on 03.05.14.
 */
public class UserOfflineException extends BusinessException {

    public UserOfflineException(String message) {
        super(message);
    }

    public UserOfflineException() {
        super();
    }

    public UserOfflineException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserOfflineException(Throwable cause) {
        super(cause);
    }
}
