package com.fly.house.web.websocket;

import com.fly.house.dao.ip.IpRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.socket.WebSocketSession;

import java.net.InetSocketAddress;
import java.security.Principal;

import static org.mockito.Mockito.*;

/**
 * Created by dimon on 4/21/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class IpNotifierHandlerTest {

    @InjectMocks
    private IpNotifierHandler handler;

    @Mock
    private IpRepository ipRepository;

    @Mock
    private WebSocketSession session;

    @Mock
    private Principal principal;

    private InetSocketAddress inetSocketAddress;

    private static final String USER_NAME = "userName";

    private static final String NOT_AUTHORIZED_USER = null;

    @Before
    public void setUp() throws Exception {
        inetSocketAddress = new InetSocketAddress("127.0.0.1", 80);
    }

    @Test
    public void afterConnectionEstablishedPutNewRecordWhenUserIsAuthenticated() throws Exception {
        when(session.getRemoteAddress()).thenReturn(inetSocketAddress);
        when(principal.getName()).thenReturn(USER_NAME);
        when(session.getPrincipal()).thenReturn(principal);

        handler.afterConnectionEstablished(session);

        verify(ipRepository).put("userName", inetSocketAddress.getAddress());
    }

    @Test
    public void afterConnectionEstablishedPutNewRecordWhenUserIsUnauthenticated() throws Exception {
        when(session.getRemoteAddress()).thenReturn(inetSocketAddress);
        when(principal.getName()).thenReturn(NOT_AUTHORIZED_USER);
        when(session.getPrincipal()).thenReturn(principal);

        handler.afterConnectionEstablished(session);

        verifyZeroInteractions(ipRepository);
    }

    @Test
    public void afterConnectionClosedShouldRemoveRecordWhenUserWasAuthenticated() throws Exception {
        when(principal.getName()).thenReturn(USER_NAME);
        when(session.getPrincipal()).thenReturn(principal);

        handler.afterConnectionClosed(session, null);

        verify(ipRepository).remove(USER_NAME);
    }

    @Test
    public void afterConnectionClosedShouldRemoveRecordWhenUserWasNotAuthenticated() throws Exception {
        when(principal.getName()).thenReturn(NOT_AUTHORIZED_USER);
        when(session.getPrincipal()).thenReturn(principal);

        handler.afterConnectionClosed(session, null);

        verifyZeroInteractions(ipRepository);
    }

}
