package com.fly.house.authentication.aspect;

import com.fly.house.authentication.AuthenticationService;
import com.fly.house.authentication.exception.AuthorizationException;
import com.fly.house.authentication.qualifier.Fake;
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

    @Fake
    @Autowired
    private AuthenticationService authenticationService;

    @Around("@annotation(com.fly.house.authentication.aspect.Secure)")
    public Object secure(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!authenticationService.isAuthorized()) {
            throw new AuthorizationException("Not authorized. No cookie was found");
        }
        return joinPoint.proceed();
    }
}
