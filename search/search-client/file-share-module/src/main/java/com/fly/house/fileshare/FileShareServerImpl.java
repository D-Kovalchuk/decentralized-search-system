package com.fly.house.fileshare;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * Created by dimon on 2/27/14.
 */
@Component
public class FileShareServerImpl implements FileShareServer {

    private static Logger logger = LoggerFactory.getLogger(FileShareServerImpl.class);

    @Autowired
    private ServerBootstrap bootstrap;

    @Autowired
    private InetSocketAddress tcpPort;

    private Channel serverChannel;

    public void start() throws Exception {
        logger.debug("Starting server at {}", tcpPort);
        serverChannel = bootstrap.bind(tcpPort)
                .sync().channel()
                .closeFuture().sync()
                .channel();
    }

    @PreDestroy
    public void stop() {
        logger.debug("Stopping server at {}...", tcpPort);
        serverChannel.close();
    }

}
