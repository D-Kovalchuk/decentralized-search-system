package com.fly.house.authentication;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by dimon on 1/26/14.
 */
//TODO password must be secure
@Component
public class Authorization {

    @Autowired
    private RestTemplate restTemplate;

    private Path cookiePath;

    public static final String AUTH_URL = "/resources/login?user={user}&password={pass}";

    public Account authentication(String login, String password) {
        ResponseEntity<Message<Account>> entity = restTemplate.exchange(AUTH_URL, HttpMethod.GET, null, new Message<Account>(), login, password);
        Message<Account> message = entity.getBody();
        if (entity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            throw new RuntimeException(message.getMessage());
        }
        List<String> cookie = entity.getHeaders().get("Cookie");
        saveCookie(cookie);
        return message.getBody();
    }

    public void logout() {
        File[] files = cookiePath.toFile().listFiles();
        for (File file : files) {
            file.delete();
        }
        //todo remove cookie
    }

    public boolean isAuthorized() {
        File[] files = cookiePath.toFile().listFiles();
        return files.length != 0;
    }

    private void saveCookie(List<String> cookie) {
        String fileName = generateFileName(cookie);
        Path path = cookiePath.resolve(fileName);
        try (OutputStream is = Files.newOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(is)) {
            oos.writeObject(cookie);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateFileName(List<String> cookie) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : cookie) {
            stringBuilder.append(s);
        }
        return DigestUtils.md5Hex(stringBuilder.toString());
    }

}
