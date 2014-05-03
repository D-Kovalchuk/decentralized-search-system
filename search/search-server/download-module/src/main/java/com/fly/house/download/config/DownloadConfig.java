package com.fly.house.download.config;

import com.fly.house.dao.config.DataAccessConfig;
import com.fly.house.model.File;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
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
@Import(DataAccessConfig.class)
@ComponentScan("com.fly.house.download.service")
@PropertySource("classpath:download.properties")
public class DownloadConfig {

    @Value("${brokerUrl}")
    private String brokerUrl;

    @Value("${destination}")
    private String destination;

    @Value("${nextDestination}")
    private String nextDestination;

    @Value("${bufferSize}")
    private int bufferSize;

    @Value("${jmsSessionCacheSize}")
    private int jmsSessionCacheSize;

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(cachingConnectionFactory());
        jmsTemplate.setDefaultDestination(destination());
        return jmsTemplate;
    }

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerUrl);
        return connectionFactory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(connectionFactory());
        connectionFactory.setSessionCacheSize(jmsSessionCacheSize);
        return connectionFactory;
    }

    @Bean
    public ActiveMQQueue destination() {
        return new ActiveMQQueue(destination);
    }

    @Bean
    public ActiveMQQueue nextDestination() {
        return new ActiveMQQueue(nextDestination);
    }

    @Bean
    public BlockingQueue<CompletableFuture<Optional<File>>> workerQueue() {
        return new LinkedBlockingQueue<>(bufferSize);
    }

}
