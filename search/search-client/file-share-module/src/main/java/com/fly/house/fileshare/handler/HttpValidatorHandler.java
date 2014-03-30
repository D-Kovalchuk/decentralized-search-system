package com.fly.house.fileshare.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.fly.house.fileshare.handler.util.HttpHelper.isNotGet;
import static com.fly.house.fileshare.handler.util.HttpHelper.sendError;
import static io.netty.channel.ChannelHandler.Sharable;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.METHOD_NOT_ALLOWED;

/**
 * Created by dimon on 3/26/14.
 */
@Sharable
@Component
public class HttpValidatorHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static Logger logger = LoggerFactory.getLogger(HttpValidatorHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (!isDecodedToHttp(request)) {
            sendError(ctx, BAD_REQUEST);
            return;
        }

        if (isNotGet(request)) {
            sendError(ctx, METHOD_NOT_ALLOWED);
            return;
        }
        ctx.fireChannelRead(request);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("{} channel active", getClass().getName());
        super.channelActive(ctx);
    }

    private boolean isDecodedToHttp(FullHttpRequest request) {
        return request.getDecoderResult().isSuccess();
    }
}
