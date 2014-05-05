package com.fly.house.download.config;

import com.fly.house.core.JmsConfig;
import com.fly.house.dao.config.DataAccessConfig;
import com.fly.house.model.File;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by dimon on 5/2/14.
 */
@Configuration
@Import({DataAccessConfig.class, JmsConfig.class})
@ComponentScan("com.fly.house.download.service")
@PropertySource("classpath:download.properties")
public class DownloadConfig {


    @Value("${destination}")
    private String destination;

    @Value("${nextDestination}")
    private String nextDestination;

    @Value("${bufferSize}")
    private int bufferSize;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Bean
    public ActiveMQQueue destination() {
        ActiveMQQueue queue = new ActiveMQQueue(destination);
        jmsTemplate.setDefaultDestination(queue);
        return queue;
    }

    @Bean
    public ActiveMQQueue nextDestination() {
        return new ActiveMQQueue(nextDestination);
    }

    @Bean
    public BlockingQueue<CompletableFuture<Optional<File>>> workerQueue() {
        return new LinkedBlockingQueue<>(bufferSize);
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }


}
