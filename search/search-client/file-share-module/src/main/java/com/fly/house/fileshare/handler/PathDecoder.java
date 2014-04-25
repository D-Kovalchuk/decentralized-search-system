package com.fly.house.fileshare.handler;

import com.fly.house.core.encrypt.PathEncryptors;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;

import static io.netty.channel.ChannelHandler.Sharable;

/**
 * Created by dimon on 3/31/14.
 */
@Component
@Sharable
public class PathDecoder extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final AttributeKey<File> pathAttr = AttributeKey.valueOf("decodedPath");
    private static Logger logger = LoggerFactory.getLogger(PathDecoder.class);

    private PathEncryptors pathEncryptors;

    @Autowired
    public PathDecoder(PathEncryptors pathEncryptors) {
        this.pathEncryptors = pathEncryptors;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String uri = request.getUri();
        Channel channel = ctx.channel();
        if (uri.equals("/favicon.ico")) {
            channel.close();
            return;
        }

        logger.debug("Hit an url {} {}", uri, "[request from " + channel.remoteAddress() + "]");
        Path decodedPath = decodePath(uri);
        logger.debug("Decoded path = {} {}", decodedPath.toString(), "[request from " + channel.remoteAddress() + "]");
        Attribute<File> attr = channel.attr(pathAttr);
        attr.set(decodedPath.toFile());

        ctx.fireChannelRead(request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("exception occur while decoding a path", cause);
        ctx.channel().close();
    }

    private Path decodePath(String uri) {
        String cleanHash = uri.replace("/", "");
        return pathEncryptors.decode(cleanHash);
    }
}
