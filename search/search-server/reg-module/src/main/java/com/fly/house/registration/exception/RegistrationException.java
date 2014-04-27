package com.fly.house.registration.exception;


import com.fly.house.core.exception.BusinessException;

/**
 * Created by dimon on 4/26/14.
 */
public class RegistrationException extends BusinessException {

    public RegistrationException(String message) {
        super(message);
    }

    public RegistrationException() {
        super();
    }

    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistrationException(Throwable cause) {
        super(cause);
    }

}
