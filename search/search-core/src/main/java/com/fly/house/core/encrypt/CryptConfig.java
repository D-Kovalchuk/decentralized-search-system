package com.fly.house.core.encrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by dimon on 3/28/14.
 */
@Configuration
@ComponentScan("com.fly.house.core.rest.encrypt")
@PropertySource("classpath:crypt.properties")
public class CryptConfig {

    @Autowired
    private Environment env;

    @Bean
    public BCryptPasswordEncoder encoder() {
        int strength = env.getProperty("strength", Integer.class);
        return new BCryptPasswordEncoder(strength);
    }


}
