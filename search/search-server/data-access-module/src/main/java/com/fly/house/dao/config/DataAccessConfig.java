package com.fly.house.dao.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by dimon on 4/27/14.
 */
@Configuration
@ComponentScan("com.fly.house.dao.repository")
@Import({EntityManagerConfig.class, RedisConfig.class})
public class DataAccessConfig {

}
