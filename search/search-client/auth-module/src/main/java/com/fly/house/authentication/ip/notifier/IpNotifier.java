package com.fly.house.authentication.ip.notifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * Created by dimon on 4/17/14.
 */
@Service
public class IpNotifier extends Endpoint {

    private static Logger logger = LoggerFactory.getLogger(IpNotifier.class);

    @Override
    public void onOpen(final Session session, EndpointConfig config) {
        logger.debug("Opening connection...");
        sendMessage(session, new File("file1"));
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        logger.debug("Session {} close because of {}", session.getId(), closeReason);
        sendMessage(session, new File("file1"));
    }

    @Override
    public void onError(Session session, Throwable thr) {
    }

    private void sendMessage(Session session, File file) {
        try {
            ByteBuffer byteBuffer = objectToByte(file);
            session.getBasicRemote().sendBinary(byteBuffer);
        } catch (IOException e) {
            logger.warn("Exception occur while sending the message: ", e);
        }
    }

    private ByteBuffer objectToByte(File file) throws IOException {
        logger.info("Convert message to ByteBuffer");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(file);
        return ByteBuffer.wrap(out.toByteArray());
    }

}
