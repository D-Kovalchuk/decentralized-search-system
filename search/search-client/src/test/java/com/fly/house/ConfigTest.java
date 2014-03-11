package com.fly.house;

import com.fly.house.ui.PresenterSpyPostProcessor;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by dimon on 3/9/14.
 */
@Profile("test")
@Configuration
@ComponentScan(value = "com.fly.house")
@PropertySource("classpath:application.properties")
public class ConfigTest {

    @Autowired
    private Environment env;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PresenterSpyPostProcessor spyPostProcessor() {
        return new PresenterSpyPostProcessor();
    }

    @Bean
    public EventBus eventBus() {
        return new EventBus();
    }

    @Bean
    public MockRestServiceServer mockRest() {
        return MockRestServiceServer.createServer(restTemplate());
    }

    @Bean
    public ThreadPoolTaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        return executor;
    }

    @Bean
    public Path pathsListFile() {
        String paths = env.getProperty("paths");
        return Paths.get(paths);
    }

    @Bean
    public Path cookiePath() {
        String cookiePath = env.getProperty("cookiePath");
        return Paths.get(cookiePath);
    }
}
