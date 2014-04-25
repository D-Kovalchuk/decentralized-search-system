package com.fly.house.io.config;

import com.fly.house.core.encrypt.CryptConfig;
import com.fly.house.core.rest.config.RestClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by dimon on 3/28/14.
 */
@Configuration
@ComponentScan("com.fly.house.io")
@Import({RestClientConfig.class, CryptConfig.class})
@PropertySource("classpath:watch-service.properties")
@ImportResource("classpath:sa-config.xml")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class WSConfig {

    @Autowired
    private Environment env;

    @Bean
    public ThreadPoolTaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        return executor;
    }

    @Bean
    @Qualifier("pathsListFile")
    public Path pathsListFile() {
        String paths = env.getProperty("paths");
        return Paths.get(paths);
    }

}
