package com.fly.house.service.registration;

import com.fly.house.dao.repository.IpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;

/**
 * Created by dimon on 4/27/14.
 */
@Service
public class OnlineServiceImpl implements OnlineService {

    @Autowired
    private IpRepository repository;


    @Override
    public void online(String userName, InetAddress ipAddress) {
        repository.put(userName, ipAddress);
    }

    @Override
    public void offline(String name) {
        repository.remove(name);
    }

    @Override
    public long size() {
        return repository.size();
    }

    @Override
    public boolean isOnline(String name) {
        return repository.isOnline(name);
    }

    @Override
    public InetAddress getAddress(String name) {
        return repository.getAddress(name);
    }
}
