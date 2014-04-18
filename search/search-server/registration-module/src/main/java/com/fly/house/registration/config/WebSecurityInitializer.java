package com.fly.house.registration.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Created by dimon on 4/17/14.
 */
public class WebSecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    public WebSecurityInitializer() {
        super(WebSecurityConfig.class);
    }
}
