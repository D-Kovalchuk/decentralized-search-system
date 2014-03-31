package com.fly.house.fileshare.handler;

import com.fly.house.fileshare.handler.util.CacheManager;
import com.fly.house.fileshare.handler.util.ContentProvider;
import com.fly.house.fileshare.handler.util.ProgressiveListener;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;

import static com.fly.house.fileshare.handler.util.HttpHelper.sendError;
import static com.fly.house.fileshare.handler.util.HttpHelper.setContentTypeHeader;
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
    protected void messageReceived(final ChannelHandlerContext ctx, FullHttpRequest request) throws FileNotFoundException {
        writeHeader(ctx, request);
        writeContent(ctx, request);
        ctx.fireChannelRead(request);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("{} channel active", getClass().getName());
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof FileNotFoundException) {
            logger.warn("file not found", cause);
            sendError(ctx, NOT_FOUND);
        }
    }

    private File getFile(FullHttpRequest request) {
        final String path = request.getUri();
        return new File(path);
    }

    private void writeContent(ChannelHandlerContext ctx, FullHttpRequest request) throws FileNotFoundException {
        AttributeKey<File> pathAttr = AttributeKey.valueOf("decodedPath");
        Attribute<File> attr = ctx.channel().attr(pathAttr);
        File file = attr.get();
        ContentProvider contentProvider = new ContentProvider(file);

        ChannelFuture sendFileFuture = ctx.write(contentProvider.getRegion(), ctx.newProgressivePromise());
        sendFileFuture.addListener(new ProgressiveListener(ctx));

        ChannelFuture lastContentFuture = ctx.writeAndFlush(EMPTY_LAST_CONTENT);
        if (!isKeepAlive(request)) {
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private void writeHeader(ChannelHandlerContext ctx, FullHttpRequest request) {
        File file = getFile(request);
        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
        setContentLength(response, file.length());
        setContentTypeHeader(response, file);
        cacheManager.setDateAndCacheHeaders(response, file);
        if (isKeepAlive(request)) {
            response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }
        ctx.write(response);
    }

}
