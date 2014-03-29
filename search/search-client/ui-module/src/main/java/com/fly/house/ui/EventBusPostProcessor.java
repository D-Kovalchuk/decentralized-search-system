package com.fly.house.ui;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by dimon on 3/6/14.
 */
@Component
public class EventBusPostProcessor implements BeanPostProcessor {

    @Autowired
    private EventBus eventBus;

    private static Logger logger = LoggerFactory.getLogger(EventBusPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            boolean isAnnotationPresent = method.isAnnotationPresent(Subscribe.class);
            if (isAnnotationPresent) {
                eventBus.register(bean);
                logger.debug("{} handler registered in the event bus", bean.getClass().getName());
                return bean;
            }
        }
        return bean;
    }
}
