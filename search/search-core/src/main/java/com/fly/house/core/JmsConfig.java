package com.fly.house.core;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.Arrays;

/**
 * Created by dimon on 05.05.14.
 */
@Configuration
public class JmsConfig {

    @Value("${brokerUrl}")
    private String brokerUrl;

    @Value("${jmsSessionCacheSize}")
    private int jmsSessionCacheSize;

    @Autowired
    private Environment env;

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(cachingConnectionFactory());
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
    public PropertySourcesPlaceholderConfigurer properties() {
        System.out.println(env);
        String[] activeProfiles = env.getActiveProfiles();
        boolean isDev = Arrays.stream(activeProfiles).anyMatch(profile -> profile.equals("dev"));
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        if (isDev) {
            Resource resources = new ClassPathResource("jms.dev.properties");
            configurer.setLocation(resources);
        } else {
            Resource resources = new ClassPathResource("jms.properties");
            configurer.setLocation(resources);
        }
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }

}
