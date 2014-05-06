package com.fly.house.index.config;

import com.fly.house.core.JmsConfig;
import com.fly.house.index.service.Indexer;
import com.fly.house.service.config.ServiceConfig;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * Created by dimon on 05.05.14.
 */
@Configuration
@Import({JmsConfig.class, ServiceConfig.class})
@ComponentScan("com.fly.house.index.service")
@PropertySource("classpath:index.properties")
public class IndexConfig {

    @Value("${destination}")
    private String destination;

    @Autowired
    private Indexer indexer;

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Bean
    public ActiveMQQueue destination() {
        return new ActiveMQQueue(destination);
    }

    @Autowired
    private Environment env;

    @Bean
    public DefaultMessageListenerContainer container() {
        System.out.println(env);
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setDestination(destination());
        container.setMessageListener(indexer);
        return container;
    }
}
