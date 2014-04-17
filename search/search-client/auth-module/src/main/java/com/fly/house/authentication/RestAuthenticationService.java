package com.fly.house.authentication;

import com.fly.house.core.profile.Production;
import com.fly.house.core.rest.CookieService;
import com.fly.house.core.rest.HttpHandler;
import com.fly.house.core.rest.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.POST;

/**
 * Created by dimon on 1/26/14.
 */
@Service
@Production
public class RestAuthenticationService implements AuthenticationService {

    private RestTemplate restTemplate;

    private CookieService cookieService;

    private HttpHandler httpHandler;

    @Value("${authUrl}")
    private String authUrl;

    @Value("${logoutUrl}")
    private String logoutUrl;

    private static Logger logger = LoggerFactory.getLogger(RestAuthenticationService.class);

    @Autowired
    public RestAuthenticationService(CookieService cookieService, RestTemplate restTemplate, HttpHandler httpHandler) {
        this.cookieService = cookieService;
        this.restTemplate = restTemplate;
        this.httpHandler = httpHandler;
    }

    @Override
    public void authentication(String login, String password) {
        logger.debug("Start authentication with login={} password={}", login, password);
        Account account = new Account(login, password);
        HttpEntity<Message<Account>> request = new HttpEntity<>(new Message<>(account));
        logger.debug("Call to server to authenticate user");
        ResponseEntity<Message<Account>> entity = restTemplate.exchange(authUrl, POST, request, new Message<Account>(), login, password);
        httpHandler.handle(entity.getStatusCode());
        List<String> cookie = entity.getHeaders().get("Cookie");
        cookieService.saveCookie(cookie);
    }

    @Override
    public void logout() {
        Message<String> message = (Message<String>) restTemplate.getForObject(logoutUrl, Message.class);
        logger.debug("logout from the service");
        if (message.getCode() == HttpStatus.OK.value()) {
            cookieService.removeCookies();
        }
    }

    @Override
    public boolean isAuthorized() {
        return cookieService.isLoaded();
    }

}
