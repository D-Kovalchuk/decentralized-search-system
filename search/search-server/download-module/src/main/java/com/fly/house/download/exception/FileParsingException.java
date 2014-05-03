package com.fly.house.download.exception;

import com.fly.house.core.exception.BusinessException;

/**
 * Created by dimon on 03.05.14.
 */
public class FileParsingException extends BusinessException {

    public FileParsingException(String message) {
        super(message);
    }

    public FileParsingException() {
        super();
    }

    public FileParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileParsingException(Throwable cause) {
        super(cause);
    }
}
