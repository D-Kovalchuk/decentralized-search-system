package com.fly.house.authentication.ip.notifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static java.util.Objects.nonNull;

/**
 * Created by dimon on 4/17/14.
 */
@Service
public class IpNotifierConnectionManager {

    @Value("${wsUrl}")
    private String wsUrl;

    private Session session;

    @Autowired
    private IpNotifier ipNotifier;

    private static Logger logger = LoggerFactory.getLogger(IpNotifierConnectionManager.class);

    public void connect() {
        final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            //todo set cookie
            session = container.connectToServer(ipNotifier, null, new URI(wsUrl));
        } catch (DeploymentException | URISyntaxException | IOException e) {
            logger.warn("Cannon connect to the server", e);
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            if (nonNull(session) && session.isOpen()) {
                session.close();
            }
        } catch (IOException e) {
            logger.warn("Cannot close connection: ", e);
        }
    }

}
