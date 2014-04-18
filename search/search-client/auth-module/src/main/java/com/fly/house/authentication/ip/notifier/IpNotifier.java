package com.fly.house.authentication.ip.notifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

/**
 * Created by dimon on 4/17/14.
 */
@Service
public class IpNotifier extends Endpoint {

    private static Logger logger = LoggerFactory.getLogger(IpNotifier.class);

    @Override
    public void onOpen(final Session session, EndpointConfig config) {
        logger.debug("Opening connection...");
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        logger.debug("Session {} close because of {}", session.getId(), closeReason);
    }

    @Override
    public void onError(Session session, Throwable thr) {
    }

}
