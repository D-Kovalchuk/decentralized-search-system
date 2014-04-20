package com.fly.house.authentication;

import com.fly.house.authentication.exception.AuthorizationException;
import com.fly.house.core.rest.CookieService;
import com.fly.house.core.rest.HttpHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by dimon on 1/31/14.
 */
//TODO integration tests
@RunWith(MockitoJUnitRunner.class)
public class RestAuthenticationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CookieService cookieService;

    @Spy
    private HttpHandler httpHandler;

    @InjectMocks
    private RestAuthenticationService authenticationService;

    private List<String> cookies;
    private HttpHeaders httpHeaders;
    private String login;
    private String password;

    @Before
    public void setUp() throws Exception {
        login = "login";
        password = "password";
        httpHeaders = new HttpHeaders();
        cookies = asList("dfwf34eewfw");
        httpHeaders.put("Set-Cookie", cookies);
    }

    @Test
    public void authenticationShouldSaveCookieWhenResponseIsOk() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("user", "login");
        map.add("password", "password");
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(httpHeaders, HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(),
                eq(map),
                eq(Void.class))
        ).thenReturn(responseEntity);

        authenticationService.authentication(login, password);

        verify(httpHandler).handle(OK);
        verify(cookieService).saveCookie(cookies);
    }


    @Test(expected = AuthorizationException.class)
    public void authenticationShouldSaveCookieWhenResponseIsUnauthorized() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("user", "login");
        map.add("password", "password");
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        when(restTemplate.postForEntity(anyString(),
                eq(map),
                eq(Void.class))
        ).thenReturn(responseEntity);

        authenticationService.authentication(login, password);
    }


}
