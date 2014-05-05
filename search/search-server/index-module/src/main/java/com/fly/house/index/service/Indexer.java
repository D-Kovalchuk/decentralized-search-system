package com.fly.house.index.service;

import com.fly.house.service.file.ArtifactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Created by dimon on 05.05.14.
 */
@Service
public class Indexer implements MessageListener {

    @Autowired
    private ArtifactService artifactService;

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage object = (ObjectMessage) message;
            Long id = (Long) object.getObject();
            artifactService.index(id);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
