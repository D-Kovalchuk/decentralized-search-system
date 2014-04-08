package com.fly.house.main;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import static org.springframework.context.annotation.ComponentScan.Filter;

/**
 * Created by dimon on 3/28/14.
 */
@Configuration
@ComponentScan(value = "com.fly.house",
        includeFilters = @Filter(type = FilterType.ANNOTATION, value = Configuration.class))
@ImportResource("classpath:spring-integration.xml")
public class AppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
