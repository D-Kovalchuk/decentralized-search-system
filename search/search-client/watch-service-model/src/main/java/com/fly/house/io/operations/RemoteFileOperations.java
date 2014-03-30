package com.fly.house.io.operations;

import com.fly.house.encrypt.PathEncryptors;
import com.fly.house.io.operations.api.FileOperation;
import com.fly.house.rest.CookieService;
import com.fly.house.rest.HttpHandler;
import com.fly.house.rest.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Path;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;

/**
 * Created by dimon on 1/26/14.
 */
@Component
public class RemoteFileOperations implements FileOperation {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CookieService cookieService;
    @Autowired
    private HttpHandler httpHandler;
    @Autowired
    private PathEncryptors pathEncryptors;
    @Value("${fileUrl}")
    public String url;
    private Message<String> responseType = new Message<>();

    @Override
    public void create(Path newPath) {
        HttpEntity<Message<String>> requestEntity = constructRequest(newPath);
        ResponseEntity<Message<String>> entity = restTemplate.exchange(url, POST, requestEntity, responseType);
        httpHandler.handle(entity.getStatusCode());
    }

    @Override
    public void delete(Path oldPath) {
        HttpEntity<Message<String>> requestEntity = constructRequest(oldPath);
        ResponseEntity<Message<String>> entity = restTemplate.exchange(url, DELETE, requestEntity, responseType);
        httpHandler.handle(entity.getStatusCode());
    }

    private HttpEntity<Message<String>> constructRequest(Path path) {
        HttpHeaders header = cookieService.getCookieHeader();
        String encodedPath = pathEncryptors.encode(path);
        Message<String> message = new Message<>(encodedPath);
        return new HttpEntity<>(message, header);
    }

}
