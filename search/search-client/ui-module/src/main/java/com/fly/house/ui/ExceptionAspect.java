package com.fly.house.ui;

import com.fly.house.authentication.exception.AuthorizationException;
import com.fly.house.ui.event.LogoutEvent;
import com.google.common.eventbus.EventBus;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dimon on 4/7/14.
 */
@Aspect
@Component
//fixme doesn't work
public class ExceptionAspect {

    @Autowired
    private EventBus eventBus;

    private static Logger logger = LoggerFactory.getLogger(ExceptionAspect.class);

    @Around("execution(* com.fly.house.ui.presenter.*.go())")
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println(pjp.getSignature());
        try {
            return pjp.proceed();
        } catch (AuthorizationException ex) {
            logger.warn("user not authenticated: ", ex);
            eventBus.post(new LogoutEvent());
        }
        return null;
    }

}
