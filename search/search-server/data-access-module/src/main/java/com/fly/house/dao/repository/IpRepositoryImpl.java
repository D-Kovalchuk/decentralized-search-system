package com.fly.house.dao.repository;

import com.fly.house.core.profile.Production;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.net.InetAddress;

/**
 * Created by dimon on 4/20/14.
 */
@Production
@Repository
public class IpRepositoryImpl implements IpRepository {

    @Autowired
    private RedisTemplate<String, InetAddress> redisTemplate;

    public static final String KEY_NAMESPACE = "users";

    private BoundHashOperations<String, String, InetAddress> hashOps;

    @PostConstruct
    public void init() {
        hashOps = redisTemplate.boundHashOps(KEY_NAMESPACE);
    }

    @Override
    public void put(String userName, InetAddress ipAddress) {
        hashOps.put(userName, ipAddress);
    }

    @Override
    public void remove(String key) {
        hashOps.delete(key);
    }

    @Override
    public boolean isOnline(String key) {
        return hashOps.hasKey(key);
    }

    @Override
    public long size() {
        return hashOps.size();
    }

    //todo test
    @Override
    public InetAddress getAddress(String name) {
        return hashOps.get(name);
    }

}
