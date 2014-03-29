package com.fly.house.main;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by dimon on 3/28/14.
 */
@Configuration
@ComponentScan("com.fly.house")
@ImportResource("classpath:spring-integration.xml")
public class AppConfig {
}
