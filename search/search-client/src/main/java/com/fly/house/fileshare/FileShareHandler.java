package com.fly.house.fileshare;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;

import static com.fly.house.fileshare.HttpHelper.sendError;
import static com.fly.house.fileshare.HttpHelper.setContentTypeHeader;
import static io.netty.channel.ChannelHandler.Sharable;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.codec.http.LastHttpContent.EMPTY_LAST_CONTENT;

/**
 * Created by dimon on 3/26/14.
 */
@Sharable
@Component
public class FileShareHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Autowired
    private CacheManager cacheManager;

    private static Logger logger = LoggerFactory.getLogger(FileShareHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("TestHandler channel active");
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, FullHttpRequest request) {
        final String path = request.getUri();
        File file = new File(path);

        ContentProvider contentProvider;
        try {
            contentProvider = new ContentProvider(file);
        } catch (FileNotFoundException e) {
            sendError(ctx, NOT_FOUND);
            return;
        }

        writeHeader(ctx, request, file);

        ChannelFuture sendFileFuture = ctx.write(contentProvider.getRegion(), ctx.newProgressivePromise());
        sendFileFuture.addListener(new ProgressiveListener(ctx));

        ChannelFuture lastContentFuture = ctx.writeAndFlush(EMPTY_LAST_CONTENT);

        if (!isKeepAlive(request)) {
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }

        ctx.fireChannelRead(request);
    }


    private void writeHeader(ChannelHandlerContext ctx, FullHttpRequest request, File file) {
        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
        setContentLength(response, file.length());
        setContentTypeHeader(response, file);
        cacheManager.setDateAndCacheHeaders(response, file);
        if (isKeepAlive(request)) {
            response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }

        // Write the initial line and the header.
        ctx.write(response);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
