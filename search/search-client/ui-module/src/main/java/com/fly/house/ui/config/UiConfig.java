package com.fly.house.ui.config;

import com.fly.house.authentication.config.AuthConfig;
import com.fly.house.io.config.WSConfig;
import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.*;

/**
 * Created by dimon on 3/28/14.
 */
@Configuration
@ComponentScan("com.fly.house.ui")
@Import({WSConfig.class, AuthConfig.class})
@EnableAspectJAutoProxy
public class UiConfig {

    @Bean
    public EventBus eventBus() {
        return new EventBus();
    }

}
