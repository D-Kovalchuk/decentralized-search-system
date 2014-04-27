package com.fly.house.registration.config;

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
@ComponentScan({"com.fly.house.registration.service", "com.fly.house.registration.aspect"})
@EnableAspectJAutoProxy
public class RegistrationConfig {

}
