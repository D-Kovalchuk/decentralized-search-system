package com.fly.house.ui.config;

import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by dimon on 3/28/14.
 */
@Configuration
@ComponentScan("com.fly.house.ui")
@EnableAspectJAutoProxy
public class UiConfig {

    @Bean
    public EventBus eventBus() {
        return new EventBus();
    }

}
