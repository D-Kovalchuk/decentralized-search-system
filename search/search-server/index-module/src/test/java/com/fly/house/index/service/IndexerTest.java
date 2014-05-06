package com.fly.house.index.service;

import com.fly.house.index.config.IndexConfig;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("dev")
@ContextConfiguration(classes = IndexConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class IndexerTest {

    @Autowired
    private ActiveMQConnectionFactory connectionFactory;


    @Autowired
    private Environment env;

    @Before
    public void setUp() throws Exception {
        System.out.println("bla" + env);
        System.out.println(connectionFactory);
//        connectionFactory.create

    }

    @Test
    public void testName() throws Exception {

        System.out.println("dsfds");

    }
}