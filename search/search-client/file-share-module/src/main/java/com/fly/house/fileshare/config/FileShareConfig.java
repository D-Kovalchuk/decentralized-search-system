package com.fly.house.fileshare.config;

import com.fly.house.core.encrypt.CryptConfig;
import com.fly.house.fileshare.ProtocolInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import java.net.InetSocketAddress;

/**
 * Created by dimon on 3/26/14.
 */
@Configuration
@ComponentScan("com.fly.house.fileshare")
@Import(CryptConfig.class)
@PropertySource("classpath:netty-config.properties")
@ImportResource("classpath:gateway-config.xml")
public class FileShareConfig {

    @Autowired
    private Environment env;

    @Autowired
    private ProtocolInitializer initializer;

    @Bean
    public ServerBootstrap bootstrap() {
        Boolean keepAlive = getProperty("so.keepalive", Boolean.class);
        Integer backlog = getProperty("so.backlog", Integer.class);
        return new ServerBootstrap().group(bossGroup(), workerGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(initializer)
                .option(ChannelOption.SO_KEEPALIVE, keepAlive)
                .option(ChannelOption.SO_BACKLOG, backlog);
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup() {
        Integer bossCount = getProperty("boss.thread.count", Integer.class);
        return new NioEventLoopGroup(bossCount);
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        Integer workerCount = getProperty("worker.thread.count", Integer.class);
        return new NioEventLoopGroup(workerCount);
    }

    @Bean
    public InetSocketAddress tcpPort() {
        Integer tcpPort = getProperty("tcp.port", Integer.class);
        return new InetSocketAddress(tcpPort);
    }

    private <T> T getProperty(String key, Class<T> type) {
        return env.getProperty(key, type);
    }
}
