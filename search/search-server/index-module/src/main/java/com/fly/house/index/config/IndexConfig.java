package com.fly.house.index.config;

import com.fly.house.core.JmsConfig;
import com.fly.house.index.service.Indexer;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * Created by dimon on 05.05.14.
 */
@Configurable
@Import(JmsConfig.class)
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

    @Bean
    public DefaultMessageListenerContainer container() {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setDestination(destination());
        container.setMessageListener(indexer);
        return container;
    }
}
