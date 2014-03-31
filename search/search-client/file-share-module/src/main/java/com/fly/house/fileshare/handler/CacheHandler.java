package com.fly.house.fileshare.handler;

import com.fly.house.fileshare.handler.util.CacheManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

import static io.netty.channel.ChannelHandler.Sharable;

/**
 * Created by dimon on 3/27/14.
 */
@Sharable
@Component
public class CacheHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Autowired
    private CacheManager cacheManager;

    private static Logger logger = LoggerFactory.getLogger(HttpValidatorHandler.class);

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        final String path = request.getUri();
        File file = new File(path);

        if (cacheManager.validateCache(ctx, request, file)) {
            logger.debug("File has been cached already {}", "[request from " + ctx.channel().remoteAddress() + "]");
            return;
        }

        ctx.fireChannelRead(request);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("{} channel active", getClass().getName());
        super.channelActive(ctx);
    }
}
