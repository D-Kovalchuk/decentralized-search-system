package com.fly.house.authentication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * Created by dimon on 1/31/14.
 */
//TODO integration tests
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-test.xml")
public class AuthorizationSpec {

    @Autowired
    private RestTemplate restTemplate;
    //    @Autowired
//    private MockRestServiceServer server;
    @Autowired
    private Authorization authorization;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test() {
//        String salt = KeyGenerators.string().generateKey();
//        StandardPasswordEncoder encoder = new StandardPasswordEncoder(salt);
//        String encryptedPassword = encoder.encode("myPassword");
//        assertTrue(encoder.matches("myPassword", encryptedPassword));
    }

    private String encryptPassword(String password) {
        String salt = KeyGenerators.string().generateKey();
        StandardPasswordEncoder encoder = new StandardPasswordEncoder(salt);
        return encoder.encode(password);
    }
}
