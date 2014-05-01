package com.fly.house.dao.repository;

import com.fly.house.core.profile.Dev;
import org.springframework.stereotype.Repository;

import java.net.InetAddress;

/**
 * Created by dimon on 5/1/14.
 */
//todo fix fake repository
@Dev
@Repository
public class FakeIpRepository implements IpRepository {

    private boolean online = true;

    @Override
    public void put(String userName, InetAddress ipAddress) {

    }

    @Override
    public void remove(String key) {

    }

    @Override
    public boolean isOnline(String key) {
        boolean tmp = online;
        online = false;
        return tmp;
    }

    @Override
    public long size() {
        return 0;
    }
}
