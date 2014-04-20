package com.fly.house.authentication;

import com.fly.house.authentication.exception.AuthorizationException;
import com.fly.house.core.exception.RestException;
import com.fly.house.core.profile.Production;
import com.fly.house.core.rest.CookieService;
import com.fly.house.core.rest.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
        logger.debug("Call to server to authenticate user");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("user", login);
        map.add("password", password);
        ResponseEntity<Void> entity = restTemplate.postForEntity(authUrl, map, Void.class);
        try {
            httpHandler.handle(entity.getStatusCode());
        } catch (RestException e) {
            throw new AuthorizationException(e);
        }
        List<String> cookie = entity.getHeaders().get("Set-Cookie");

        logger.debug("Cookie was retrieved {}", cookie);
        cookieService.saveCookie(cookie);
    }

    @Override
    public void logout() {
        ResponseEntity<Void> entity = restTemplate.getForEntity(logoutUrl, Void.class);
        logger.debug("logout from the service");
        if (entity.getStatusCode() == HttpStatus.OK) {
            cookieService.removeCookies();
        }
    }

    @Override
    public boolean isAuthorized() {
        return cookieService.isLoaded();
    }

}
