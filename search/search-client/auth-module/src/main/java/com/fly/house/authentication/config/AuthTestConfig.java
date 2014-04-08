package com.fly.house.authentication.config;

import com.fly.house.authentication.AuthenticationService;
import com.fly.house.authentication.FakeAuthenticationService;
import org.springframework.context.annotation.*;

/**
 * Created by dimon on 4/8/14.
 */
@Profile("test")
@Configuration
@ComponentScan("com.fly.house.authentication")
@EnableAspectJAutoProxy
public class AuthTestConfig {

    @Bean
    public AuthenticationService authenticationService() {
        return new FakeAuthenticationService(false);
    }
}
