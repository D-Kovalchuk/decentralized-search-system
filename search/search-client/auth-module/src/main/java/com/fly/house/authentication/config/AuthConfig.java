package com.fly.house.authentication.config;

import com.fly.house.core.rest.config.RestClientConfig;
import org.springframework.context.annotation.*;

/**
 * Created by dimon on 3/28/14.
 */
@Configuration
@ComponentScan("com.fly.house.authentication")
@PropertySource("classpath:auth.properties")
@Import(RestClientConfig.class)
@EnableAspectJAutoProxy
public class AuthConfig {
}
