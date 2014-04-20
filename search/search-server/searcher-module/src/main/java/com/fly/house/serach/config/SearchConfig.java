package com.fly.house.serach.config;

import com.fly.house.dao.config.DatabaseConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by dimon on 4/20/14.
 */
@Configuration
@Import(DatabaseConfig.class)
public class SearchConfig {

}
