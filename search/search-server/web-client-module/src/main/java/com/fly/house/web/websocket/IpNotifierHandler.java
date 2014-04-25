package com.fly.house.web.websocket;

import com.fly.house.dao.service.security.ip.IpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.net.InetAddress;

import static java.util.Objects.nonNull;

/**
 * Created by dimon on 4/17/14.
 */
@Service
public class IpNotifierHandler extends BinaryWebSocketHandler {

    private static Logger logger = LoggerFactory.getLogger(IpNotifierHandler.class);

    @Autowired
    private IpRepository ipRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Connection established");
        InetAddress ipAddress = session.getRemoteAddress().getAddress();
        String userName = getUserName(session);
        if (nonNull(userName)) {
            logger.debug("Saving into database key={} , value={}", userName, ipAddress);
            ipRepository.put(userName, ipAddress);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("Session {} close because of {}", session.getId(), status);
        String userName = getUserName(session);
        if (nonNull(userName)) {
            logger.debug("Removing from database key={}", userName);
            ipRepository.remove(userName);
        }
    }

    private String getUserName(WebSocketSession session) {
        if (nonNull(session.getPrincipal())) {
            return session.getPrincipal().getName();
        }
        return null;
    }
}
