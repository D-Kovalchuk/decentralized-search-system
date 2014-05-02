package com.fly.house.dao.repository;

import java.net.InetAddress;

/**
 * Created by dimon on 4/30/14.
 */
public interface IpRepository {

    void put(String userName, InetAddress ipAddress);

    void remove(String key);

    boolean isOnline(String key);

    long size();

}
