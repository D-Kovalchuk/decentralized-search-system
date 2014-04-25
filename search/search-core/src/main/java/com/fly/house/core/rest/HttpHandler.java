package com.fly.house.core.rest;

import com.fly.house.core.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Created by dimon on 2/26/14.
 */
@Component
public class HttpHandler {

    public void handle(HttpStatus status) {
        if (status == HttpStatus.UNAUTHORIZED) {
            throw new UnauthorizedException();
        }
    }

}
