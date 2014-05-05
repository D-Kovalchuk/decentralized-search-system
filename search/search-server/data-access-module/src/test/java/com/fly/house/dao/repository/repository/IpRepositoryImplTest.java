package com.fly.house.dao.repository.repository;

import com.fly.house.dao.config.DataAccessConfig;
import com.fly.house.dao.repository.IpRepository;
import com.fly.house.dao.repository.IpRepositoryImpl;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.embedded.RedisServer;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Created by dimon on 4/20/14.
 */
@ActiveProfiles("production")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessConfig.class)
@Ignore
public class IpRepositoryImplTest {

    @Autowired
    private IpRepository ipRepository;

    @Autowired
    private RedisTemplate<String, InetAddress> redisTemplate;

    private BoundHashOperations<String, String, InetAddress> hashOps;

    public static final String KEY = "userName";

    public static InetAddress IP;

    private static RedisServer redisServer;

    @BeforeClass
    public static void startRedis() throws IOException {
        IP = InetAddress.getLocalHost();
        redisServer = new RedisServer(new File("/home/dimon/Programs/redis-2.8.8/src/redis-server"), 6379);
        redisServer.start();
    }

    @Before
    public void setUp() throws Exception {
        hashOps = redisTemplate.boundHashOps(IpRepositoryImpl.KEY_NAMESPACE);
    }

    @AfterClass
    public static void stoopRedis() throws IOException, InterruptedException {
        redisServer.stop();
    }

    @After
    public void tearDown() throws Exception {
        redisTemplate.getConnectionFactory()
                .getConnection()
                .flushAll();
    }

    @Test
    public void putShouldSetNewValueIntoRedis() {
        ipRepository.put(KEY, IP);

        InetAddress actualValue = hashOps.get(KEY);
        assertThat(actualValue, equalTo(IP));
    }

    @Test
    public void putShouldRewriteValueOfExistedKey() throws UnknownHostException {
        hashOps.put(KEY, IP);

        InetAddress localhost = InetAddress.getByName("localhost");
        ipRepository.put(KEY, localhost);

        InetAddress actualValue = hashOps.get(KEY);
        assertThat(actualValue, equalTo(localhost));
    }


    @Test
    public void isOnlineShouldReturnTrueIfKeyExists() {
        hashOps.put(KEY, IP);

        boolean isOnline = ipRepository.isOnline(KEY);

        assertTrue(isOnline);
    }

    @Test
    public void isOnlineShouldReturnFalseIfKeyDoesNotExist() {
        boolean isOnline = ipRepository.isOnline(KEY);

        assertFalse(isOnline);
    }

    @Test
    public void removeShouldRemoveValueWhenKeyExists() {
        hashOps.put(KEY, IP);

        ipRepository.remove(KEY);

        Boolean isAbsent = hashOps.hasKey(KEY);
        assertFalse(isAbsent);
    }


    @Test
    public void removeShouldDoNothingIfRecordWasNotAdded() {
        ipRepository.remove(KEY);

        Boolean isAbsent = hashOps.hasKey(KEY);
        assertFalse(isAbsent);
    }

    @Test
    public void sizeShouldReturnZeroWhenDBisEmpty() {
        long size = ipRepository.size();

        assertThat(size, equalTo(0L));
    }

    @Test
    public void sizeShouldReturnOneWhenDBContainsOneRecord() {
        hashOps.put(KEY, IP);

        long size = ipRepository.size();

        assertThat(size, equalTo(1L));
    }

}
