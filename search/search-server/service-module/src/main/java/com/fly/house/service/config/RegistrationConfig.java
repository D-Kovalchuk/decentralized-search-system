package com.fly.house.service.config;

import com.fly.house.dao.config.DataAccessConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * Created by dimon on 4/18/14.
 */
@Configuration
@Import(DataAccessConfig.class)
@ComponentScan({"com.fly.house.service.registration", "com.fly.house.service.aspect"})
@EnableAspectJAutoProxy
public class RegistrationConfig {

}
