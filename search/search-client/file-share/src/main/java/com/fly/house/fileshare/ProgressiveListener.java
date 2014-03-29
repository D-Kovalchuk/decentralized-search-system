package com.fly.house.fileshare;

import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.netty.handler.codec.http.LastHttpContent.EMPTY_LAST_CONTENT;

/**
 * Created by dimon on 3/26/14.
 */
public class ProgressiveListener implements ChannelProgressiveFutureListener {

    private ChannelHandlerContext ctx;

    private static Logger logger = LoggerFactory.getLogger(ProgressiveListener.class);

    public ProgressiveListener(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) {
        if (total < 0) {
            logger.debug("Transfer progress: {}" + progress);
        } else {
            logger.debug("Transfer progress: {} / {} {}", progress, total, "[request from " + ctx.channel().remoteAddress() + "]");
        }
    }

    @Override
    public void operationComplete(ChannelProgressiveFuture future) throws Exception {
        logger.debug("Transfer complete.");
        ChannelFuture lastContentFuture = ctx.writeAndFlush(EMPTY_LAST_CONTENT);
        lastContentFuture.addListener(ChannelFutureListener.CLOSE);
    }
}
