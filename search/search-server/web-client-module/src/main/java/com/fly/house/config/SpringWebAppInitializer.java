package com.fly.house.config;

import com.fly.house.config.mvc.MvcConfig;
import com.fly.house.config.mvc.WebSocketConfig;
import com.fly.house.config.security.WebSecurityConfig;
import com.fly.house.serach.config.SearchConfig;
import com.fly.house.service.config.RegistrationConfig;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Created by dimon on 4/2/14.
 */
public class SpringWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{WebSecurityConfig.class, RegistrationConfig.class, SearchConfig.class};
    }


    @Override
    protected WebApplicationContext createRootApplicationContext() {
        WebApplicationContext context = super.createRootApplicationContext();
        ConfigurableEnvironment environment = (ConfigurableEnvironment) context.getEnvironment();
        environment.setActiveProfiles("production");
        return context;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{MvcConfig.class, WebSocketConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{
                new OpenEntityManagerInViewFilter()
        };
    }
}
