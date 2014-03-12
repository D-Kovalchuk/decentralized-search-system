package com.fly.house.io.operations;

import com.fly.house.authentication.CookieManager;
import com.fly.house.authentication.HttpStatusHandler;
import com.fly.house.authentication.Message;
import com.fly.house.io.operations.api.FileOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CookieManager cookieManager;
    @Autowired
    private HttpStatusHandler httpHandler;
    private Message<String> responseType = new Message<>();
    public static final String URL = "http://localhost:8080/resources/file";

    @Override
    public void create(Path newPath) {
        HttpHeaders header = cookieManager.getCookieHeader();
        Message<String> message = new Message<>(newPath.toString());
        HttpEntity<Message<String>> requestEntity = new HttpEntity<>(message, header);
        ResponseEntity<Message<String>> entity = restTemplate.exchange(URL, POST, requestEntity, responseType);
        httpHandler.handle(entity.getStatusCode());
    }

    @Override
    public void delete(Path oldPath) {
        HttpHeaders header = cookieManager.getCookieHeader();
        Message<String> message = new Message<>(oldPath.toString());
        HttpEntity<Message<String>> requestEntity = new HttpEntity<>(message, header);
        ResponseEntity<Message<String>> entity = restTemplate.exchange(URL, DELETE, requestEntity, responseType);
        httpHandler.handle(entity.getStatusCode());
    }

}
