package com.fly.house.dao.service.security.ip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.InetAddress;

/**
 * Created by dimon on 4/20/14.
 */
@Service
public class IpRepository {

    @Autowired
    private RedisTemplate<String, InetAddress> redisTemplate;

    public static final String KEY_NAMESPACE = "users";

    private BoundHashOperations<String, String, InetAddress> hashOps;

    @PostConstruct
    public void init() {
        hashOps = redisTemplate.boundHashOps(KEY_NAMESPACE);
    }

    public void put(String userName, InetAddress ipAddress) {
        hashOps.put(userName, ipAddress);
    }

    public void remove(String key) {
        hashOps.delete(key);
    }

    public boolean isOnline(String key) {
        return hashOps.hasKey(key);
    }

    public long size() {
        return hashOps.size();
    }


}
