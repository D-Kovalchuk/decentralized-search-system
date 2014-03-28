package com.fly.house.io.repositories;

import com.fly.house.io.repositories.api.AbstractPathRepository;
import com.fly.house.io.repositories.api.PathRepo;
import com.fly.house.io.repositories.api.RemoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Path;
import java.util.List;

/**
 * Created by dimon on 1/29/14.
 */
@RemoteRepo
@PathRepo
public class RemotePathRepository extends AbstractPathRepository {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${getPathsUrl}")
    private String getPathsUrl;

    @Value("${getPathsUrl}")
    private String modifPathUrl;

    @Override
    public void add(Path path) {
        super.add(path);
//        restTemplate.postForEntity();
    }

    @Override
    public void remove(Path path) {
        super.remove(path);
    }

    @Override
    public List<Path> getPaths() {
        return null;
    }

    //todo implement this class
}
