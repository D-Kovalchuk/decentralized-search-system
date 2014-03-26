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

    @Value("${restUrl}")
    private String restUrl;

    public static final String GET_DIR_URL = "folders";

    public static final String MODIF_DIR_URL = "folders/{name}";

    @Override
    public void add(Path path) {
        super.add(path);
        String url = buildUrl(MODIF_DIR_URL);
//        restTemplate.postForEntity();
    }

    @Override
    public void remove(Path path) {
        super.remove(path);
        String url = buildUrl(MODIF_DIR_URL);
    }

    @Override
    public List<Path> getPaths() {
        String url = buildUrl(GET_DIR_URL);
        return null;
    }

    private String buildUrl(String resurceUrl) {
        return String.format("%s/%s", restUrl, resurceUrl);
    }
    //todo implement this class
}
