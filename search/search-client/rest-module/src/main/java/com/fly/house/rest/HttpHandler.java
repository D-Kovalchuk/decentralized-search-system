package com.fly.house.rest;

import com.fly.house.rest.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * Created by dimon on 2/26/14.
 */
@Component
public class HttpHandler {

    public void handle(HttpStatus status) {
        if (status == UNAUTHORIZED) {
            throw new UnauthorizedException();
        }
    }

}
