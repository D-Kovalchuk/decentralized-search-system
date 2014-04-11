package com.fly.house.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by dimon on 4/9/14.
 */
public class SystemLoginEvent extends ApplicationEvent {

    public SystemLoginEvent(Object source) {
        super(source);
    }

}
