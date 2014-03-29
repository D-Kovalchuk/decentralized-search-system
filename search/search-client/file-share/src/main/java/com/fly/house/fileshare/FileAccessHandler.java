package com.fly.house.fileshare;

import com.fly.house.io.WatchServiceStorage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchService;
import java.util.Map;

import static com.fly.house.fileshare.HttpHelper.sendError;
import static io.netty.channel.ChannelHandler.Sharable;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static java.util.Objects.isNull;

/**
 * Created by dimon on 3/27/14.
 */
@Sharable
@Component
public class FileAccessHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private WatchServiceStorage storage;

    private static Logger logger = LoggerFactory.getLogger(FileAccessHandler.class);

    public FileAccessHandler() {
    }

    @Autowired
    public FileAccessHandler(WatchServiceStorage storage) {
        this.storage = storage;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        final String path = request.getUri();
        logger.debug("Hit an url {}", path);
        File file = new File(path);
        if (isPathOnTrack(file)) {
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

    private boolean isPathOnTrack(File file) {
        Map<Path, WatchService> map = storage.asMap();
        Path pathToFile = file.toPath();
        Path parentToDir = pathToFile.getParent();
        return isNull(map.get(parentToDir));
    }
}
