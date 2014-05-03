package com.fly.house.download.service;

import com.fly.house.core.dto.PathPackage;
import com.fly.house.download.model.Converter;
import com.fly.house.download.model.DownloadInfo;
import com.fly.house.model.Artifact;
import com.fly.house.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

/**
 * Created by dimon on 03.05.14.
 */
@Service
public class MessagingService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("nextDestination")
    private Destination nextDestination;

    @Autowired
    private Converter converter;

    public DownloadInfo receiveAndConvert() {
        PathPackage pathPackage = (PathPackage) jmsTemplate.receiveAndConvert();
        //todo handle UserOffline Exception
        return converter.convert(pathPackage);
    }

    public void sendNext(File file) {
        Artifact artifact = file.getArtifact();
        Long id = artifact.getId();
        jmsTemplate.convertAndSend(nextDestination, id);
    }

    public void sendBack(PathPackage pathPackage) {
        jmsTemplate.convertAndSend(pathPackage);
    }

}
