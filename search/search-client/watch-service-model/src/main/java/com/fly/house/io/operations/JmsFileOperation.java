package com.fly.house.io.operations;

import com.fly.house.authentication.AccountRepository;
import com.fly.house.core.dto.AccountDto;
import com.fly.house.core.dto.PathPackage;
import com.fly.house.io.operations.api.FileOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.nio.file.Path;

/**
 * Created by dimon on 05.05.14.
 */
@Service
public class JmsFileOperation implements FileOperation {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    @Qualifier("deleteDestination")
    private Destination deleteQueue;

    @Autowired
    @Qualifier("createDestination")
    private Destination createQueue;

    @Value("${port}")
    private int port;

    @Override
    public void create(Path path) {
        send(path, createQueue);
    }

    @Override
    public void delete(Path path) {
        send(path, deleteQueue);
    }

    private void send(Path path, Destination destination) {
        AccountDto accountDto = accountRepository.get();
        PathPackage pathPackage = new PathPackage(accountDto, path.toString(), port);

        jmsTemplate.convertAndSend(destination, pathPackage);
    }
}
