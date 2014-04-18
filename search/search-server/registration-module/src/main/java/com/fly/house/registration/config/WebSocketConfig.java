package com.fly.house.registration.config;

import com.fly.house.registration.handler.IpNotifierHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.jetty.JettyRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;


/**
 * Created by dimon on 4/17/14.
 */
@Configuration
@EnableWebSocket
@ComponentScan("com.fly.house.registration")
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private IpNotifierHandler handler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handler, "/redis").setHandshakeHandler(handshakeHandler());
    }

    @Bean
    public DefaultHandshakeHandler handshakeHandler() {
        return new DefaultHandshakeHandler(
                new JettyRequestUpgradeStrategy());
    }
}
