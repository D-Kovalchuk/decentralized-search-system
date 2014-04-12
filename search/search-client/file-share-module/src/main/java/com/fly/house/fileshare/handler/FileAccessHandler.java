package com.fly.house.fileshare.handler;

import com.fly.house.fileshare.integration.PathService;
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

import static com.fly.house.fileshare.handler.util.HttpHelper.sendError;
import static io.netty.channel.ChannelHandler.Sharable;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;

/**
 * Created by dimon on 3/27/14.
 */
@Sharable
@Component
public class FileAccessHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private PathService pathService;
    private static Logger logger = LoggerFactory.getLogger(FileAccessHandler.class);

    public FileAccessHandler() {
    }

    @Autowired
    public FileAccessHandler(PathService pathService) {
        this.pathService = pathService;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        AttributeKey<File> pathAttr = AttributeKey.valueOf("decodedPath");
        Attribute<File> attr = ctx.channel().attr(pathAttr);
        File file = attr.get();
        if (!isPathOnTrack(file.toPath())) {
            sendError(ctx, FORBIDDEN);
            logger.debug("you don't have an access {}", "[request from " + ctx.channel().remoteAddress() + "]");
            return;
        }

        if (notExists(file)) {
            logger.debug("File doesn't exists {}", "[request from " + ctx.channel().remoteAddress() + "]");
            sendError(ctx, NOT_FOUND);
            return;
        }

        if (isNotFile(file)) {
            logger.debug("Specified path is not a path to file {}", "[request from " + ctx.channel().remoteAddress() + "]");
            sendError(ctx, FORBIDDEN);
            return;
        }

        ctx.fireChannelRead(request);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("{} channel active", getClass().getName());
        super.channelActive(ctx);
    }

    private boolean isNotFile(File file) {
        return !file.isFile();
    }

    private boolean notExists(File file) {
        return !file.exists();
    }

    private boolean isPathOnTrack(Path path) {
        Path pathToFolder = path.getParent();
        return pathService.isPathTracked(pathToFolder);
    }
}
