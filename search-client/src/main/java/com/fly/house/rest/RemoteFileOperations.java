package com.fly.house.rest;

import com.fly.house.authentication.CookieLoader;
import com.fly.house.authentication.Message;
import com.fly.house.io.api.FileOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Path;

import static org.springframework.http.HttpMethod.*;

/**
 * Created by dimon on 1/26/14.
 */
@Component
public class RemoteFileOperations implements FileOperation {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CookieLoader cookieLoader;

    private Message<Path> responseType;

    public RemoteFileOperations() {
        responseType = new Message<>();
    }

    @Override
    public void create(Path newPath) {
        HttpHeaders header = cookieLoader.getCookieHeader();
        Message<Path> message = new Message<>(newPath);
        HttpEntity<Message<Path>> requestEntity = new HttpEntity<>(message, header);
        ResponseEntity<Message<Path>> entity = restTemplate.exchange("http://localhost:8080/resources/file",
                POST, requestEntity, responseType);
        System.out.println("create");
    }

    @Override
    public void update(Path newPath, Path oldPath) {
        HttpHeaders header = cookieLoader.getCookieHeader();
        Message<Path> message = new Message<>(newPath);
        HttpEntity<Message<Path>> requestEntity = new HttpEntity<>(message, header);
        ResponseEntity<Message<Path>> entity = restTemplate.exchange("http://localhost:8080/resources/file",
                PUT, requestEntity, responseType);
        System.out.println("update");
    }

    @Override
    public void delete(Path oldPath) {
        HttpHeaders header = cookieLoader.getCookieHeader();
        Message<Path> message = new Message<>(oldPath);
        HttpEntity<Message<Path>> requestEntity = new HttpEntity<>(message, header);
        ResponseEntity<Message<Path>> entity = restTemplate.exchange("http://localhost:8080/resources/file",
                DELETE, requestEntity, responseType);
        System.out.println("delete");
    }

}
