package com.fly.house.authentication;

import com.fly.house.rest.CookieService;
import com.fly.house.rest.HttpHandler;
import com.fly.house.rest.Message;
import com.fly.house.rest.exception.UnauthorizedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * Created by dimon on 1/31/14.
 */
//TODO integration tests
@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CookieService cookieService;

    @Spy
    private HttpHandler httpHandler;

    @InjectMocks
    private AuthenticationService authenticationService;

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
        httpHeaders.put("Cookie", cookies);
    }

    @Test
    public void authenticationShouldSaveCookieWhenResponseIsOk() {
        ResponseEntity<Message<Account>> responseEntity = new ResponseEntity<>(new Message<Account>(), httpHeaders, OK);
        when(restTemplate.exchange(anyString(),
                eq(POST),
                any(HttpEntity.class),
                any(Message.class),
                eq(login),
                eq(password))
        ).thenReturn(responseEntity);

        authenticationService.authentication(login, password);

        verify(httpHandler).handle(OK);
        verify(cookieService).saveCookie(cookies);
    }

    @Test(expected = UnauthorizedException.class)
    public void authenticationShouldSaveCookieWhenResponseIsUnauthorized() {
        ResponseEntity<Message<Account>> responseEntity = new ResponseEntity<>(new Message<Account>(), httpHeaders, UNAUTHORIZED);
        when(restTemplate.exchange(anyString(),
                eq(POST),
                any(HttpEntity.class),
                any(Message.class),
                eq(login),
                eq(password))
        ).thenReturn(responseEntity);

        authenticationService.authentication(login, password);
    }


}
