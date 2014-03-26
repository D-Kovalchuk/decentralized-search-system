package com.fly.house.fileshare;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.fly.house.fileshare.HttpHelper.isPost;
import static com.fly.house.fileshare.HttpHelper.sendError;
import static io.netty.channel.ChannelHandler.Sharable;
import static io.netty.handler.codec.http.HttpResponseStatus.*;

/**
 * Created by dimon on 3/26/14.
 */
@Sharable
@Component
public class ValidatorHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static Logger logger = LoggerFactory.getLogger(ValidatorHandler.class);

    @Autowired
    private CacheManager cacheManager;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("ValidatorHandler channel active");
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        final String path = request.getUri();
        logger.debug("Hit an url {}", path);

        File file = new File(path);

        if (!isDecodedToHttp(request)) {
            sendError(ctx, BAD_REQUEST);
            return;
        }

        if (isPost(request)) {
            sendError(ctx, METHOD_NOT_ALLOWED);
            return;
        }


        if (!file.exists()) {
            logger.debug("File doesn't exists");
            sendError(ctx, NOT_FOUND);
            return;
        }

        if (!file.isFile()) {
            logger.debug("Specified path is not a path to file");
            sendError(ctx, FORBIDDEN);
            return;
        }

        if (cacheManager.validateCache(ctx, request, file)) {
            logger.debug("File has been cached already");
            return;
        }
        ctx.fireChannelRead(request);

    }

    private boolean isDecodedToHttp(FullHttpRequest request) {
        return request.getDecoderResult().isSuccess();
    }
}
