package com.fly.house.core.encrypt.exception;

import com.fly.house.core.exception.WSApplicationException;

/**
 * Created by dimon on 3/31/14.
 */
public class EncryptException extends WSApplicationException {

    public EncryptException() {
        super();
    }

    public EncryptException(String message) {
        super(message);
    }

    public EncryptException(String message, Throwable cause) {
        super(message, cause);
    }

    public EncryptException(Throwable cause) {
        super(cause);
    }
}
