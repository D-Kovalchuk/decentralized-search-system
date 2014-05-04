package com.fly.house.service.exception;

import com.fly.house.core.exception.BusinessException;

/**
 * Created by dimon on 04.05.14.
 */
public class FileManipulationException extends BusinessException {

    public FileManipulationException(String message) {
        super(message);
    }

    public FileManipulationException() {
        super();
    }

    public FileManipulationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileManipulationException(Throwable cause) {
        super(cause);
    }
}
