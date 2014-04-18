package com.fly.house.registration.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.*;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dimon on 4/17/14.
 */
@Service
public class IpNotifierHandler extends BinaryWebSocketHandler {

    private static Logger logger = LoggerFactory.getLogger(IpNotifierHandler.class);

    private ConcurrentHashMap<String, WebSocketSession> connectionTable;

    public IpNotifierHandler() {
        this.connectionTable = new ConcurrentHashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Connection established");
        InetAddress ipAddress = session.getRemoteAddress().getAddress();
        String userName = session.getPrincipal().getName();
        connectionTable.putIfAbsent(userName, session);
        //add to data base
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        InetAddress ipAddress = session.getRemoteAddress().getAddress();
        String userName = session.getPrincipal().getName();
        File file = bytesToObject(message);

        //add to data base
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.debug("");
        String userName = session.getPrincipal().getName();
        connectionTable.remove(userName);
    }

    private File bytesToObject(BinaryMessage message) throws IOException, ClassNotFoundException {
        logger.debug("Convert bytes to object");
        int length = message.getPayloadLength();
        byte[] bytes = new byte[length];
        ByteBuffer payload = message.getPayload();
        payload.get(bytes);
        try (InputStream in = new ByteArrayInputStream(bytes);
             ObjectInputStream inputStream = new ObjectInputStream(in)) {
            return (File) inputStream.readObject();
        }
    }
}
