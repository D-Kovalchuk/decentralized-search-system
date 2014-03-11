package com.fly.house.ui;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import static org.mockito.Mockito.spy;

/**
 * Created by dimon on 3/6/14.
 */
public class PresenterSpyPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        String className = bean.getClass().getName();
        if (className.endsWith("PresenterImpl")
                || className.startsWith("com.fly.house.authentication")
                || className.startsWith("com.fly.house.io")) {
            return spy(bean);
        }
        return bean;
    }
}
