package com.fly.house.authentication;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.newInputStream;

/**
 * Created by dimon on 1/31/14.
 */
@Component
public class CookieLoader {

    private Path cookiePath;
    private HttpHeaders cookieHeader;

    public HttpHeaders getCookieHeader() {
        if (cookieHeader == null) {
            cookieHeader = init();
            return cookieHeader;
        }
        return cookieHeader;
    }

    private HttpHeaders init() {
        HttpHeaders headers = null;
        File file = cookiePath.toFile();
        String[] cookieNames = file.list();
        if (cookieNames.length == 0) {
            //todo throw exception
        }
        String cookieName = cookieNames[0];
        try (InputStream is = newInputStream(cookiePath.resolve(cookieName));
             ObjectInputStream oos = new ObjectInputStream(is)) {
            List<String> values = (List<String>) oos.readObject();
            headers = new HttpHeaders();
            headers.put("Cookie", values);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return headers;
    }

}
