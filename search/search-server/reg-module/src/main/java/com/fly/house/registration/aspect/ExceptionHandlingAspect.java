package com.fly.house.registration.aspect;

import com.fly.house.registration.exception.RegistrationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

/**
 * Created by dimon on 4/27/14.
 */
@Aspect
@Component
public class ExceptionHandlingAspect {

    private static Logger logger = LoggerFactory.getLogger(ExceptionHandlingAspect.class);

    @Around("execution(public * com.fly.house.registration.service.*.*(..))")
    public Object translate(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (DataAccessException e) {
            logger.warn("Exception occur while modifying database", e);
            throw new RegistrationException(e);
        } catch (IndexOutOfBoundsException e) {
            logger.warn("List does not have enough elements", e);
            throw new RegistrationException(e);
        } catch (IllegalArgumentException e) {
            logger.warn("Exception occur while encoding password", e);
            throw new RegistrationException(e);
        } catch (Throwable t) {
            logger.warn("Unknown exception was thrown", t);
            throw new RegistrationException(t);
        }
    }
}
