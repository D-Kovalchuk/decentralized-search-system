package com.fly.house.authentication;

import com.fly.house.authentication.exception.AuthorizationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dimon on 4/7/14.
 */
@Aspect
@Component
public class AuthorizationAspect {

    @Autowired
    private AuthenticationService authenticationService;

    @Around("@annotation(com.fly.house.authentication.Secure) ")
    public Object secure(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!authenticationService.isAuthorized()) {
            throw new AuthorizationException();
        }
        return joinPoint.proceed();
    }
}
