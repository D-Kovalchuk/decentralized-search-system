package com.fly.house.dao.config;

import com.fly.house.core.profile.Dev;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * Created by dimon on 4/26/14.
 */
@Dev
@Configuration
@PropertySource("classpath:embedded.database.config.properties")
public class EmbeddedDataSourceConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource embeddedDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
        return dataSource;
    }

}
