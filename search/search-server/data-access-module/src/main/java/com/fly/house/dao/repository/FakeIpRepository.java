package com.fly.house.dao.repository;

import com.fly.house.core.profile.Dev;
import org.springframework.stereotype.Repository;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dimon on 5/1/14.
 */
@Dev
@Repository
public class FakeIpRepository implements IpRepository {

    private Map<String, InetAddress> redis = new HashMap<>();

    @Override
    public void put(String userName, InetAddress ipAddress) {
        redis.put(userName, ipAddress);
    }

    @Override
    public void remove(String key) {
        redis.remove(key);
    }

    @Override
    public boolean isOnline(String key) {
        return redis.containsKey(key);
    }

    @Override
    public long size() {
        return redis.size();
    }
}
