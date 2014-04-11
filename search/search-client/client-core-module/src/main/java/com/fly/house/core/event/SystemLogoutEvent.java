package com.fly.house.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by dimon on 4/9/14.
 */
public class SystemLogoutEvent extends ApplicationEvent {

    public SystemLogoutEvent(Object source) {
        super(source);
    }

}
