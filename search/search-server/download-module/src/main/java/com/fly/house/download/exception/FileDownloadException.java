package com.fly.house.download.exception;

import com.fly.house.core.exception.BusinessException;

/**
 * Created by dimon on 03.05.14.
 */
public class FileDownloadException extends BusinessException {

    public FileDownloadException(String message) {
        super(message);
    }

    public FileDownloadException() {
        super();
    }

    public FileDownloadException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileDownloadException(Throwable cause) {
        super(cause);
    }
}
