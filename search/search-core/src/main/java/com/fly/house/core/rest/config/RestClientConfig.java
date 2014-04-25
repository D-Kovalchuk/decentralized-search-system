package com.fly.house.core.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by dimon on 4/13/14.
 */
@Configuration
@ComponentScan("com.fly.house.core")
@PropertySource("classpath:rest.properties")
public class RestClientConfig {

    @Autowired
    private Environment env;

    @Bean
    @Qualifier("cookiePath")
    public Path cookiePath() {
        String cookiePath = env.getProperty("cookiePath");
        return Paths.get(cookiePath);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
