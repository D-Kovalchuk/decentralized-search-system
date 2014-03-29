package com.fly.house.io.repositories;

import com.fly.house.authentication.CookieManager;
import com.fly.house.authentication.Message;
import com.fly.house.encrypt.PathEncryptors;
import com.fly.house.io.repositories.api.AbstractPathRepository;
import com.fly.house.io.repositories.api.PathRepo;
import com.fly.house.io.repositories.api.RemoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;

/**
 * Created by dimon on 1/29/14.
 */
//todo tests
@RemoteRepo
@PathRepo
public class RemotePathRepository extends AbstractPathRepository {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${getPathsUrl}")
    private String getPathsUrl;

    @Value("${getPathsUrl}")
    private String modifPathUrl;

    @Autowired
    private PathEncryptors pathEncryptors;

    @Autowired
    private CookieManager cookieManager;

    private Message<String> responseType = new Message<>();

    @Override
    public void add(Path path) {
        super.add(path);
        HttpEntity<Message<String>> requestEntity = constructRequest(path);
        restTemplate.exchange(modifPathUrl, POST, requestEntity, responseType);
    }

    @Override
    public void remove(Path path) {
        super.remove(path);
        HttpEntity<Message<String>> requestEntity = constructRequest(path);
        restTemplate.exchange(modifPathUrl, DELETE, requestEntity, responseType);
    }

    @Override
    public List<Path> getPaths() {
        List<String> paths = (List<String>) restTemplate.getForObject(getPathsUrl, List.class);
        return paths.stream()
                .map(s -> Paths.get(s))
                .collect(toList());
    }

    private HttpEntity<Message<String>> constructRequest(Path path) {
        HttpHeaders header = cookieManager.getCookieHeader();
        String encodedPath = pathEncryptors.encode(path);
        Message<String> message = new Message<>(encodedPath);
        return new HttpEntity<>(message, header);
    }
}
