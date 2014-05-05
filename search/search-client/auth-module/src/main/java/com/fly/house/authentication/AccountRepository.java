package com.fly.house.authentication;

import com.fly.house.authentication.exception.AccountCannotBeSavedException;
import com.fly.house.authentication.exception.AccountNotFoundException;
import com.fly.house.core.dto.AccountDto;
import com.fly.house.core.event.SystemLogoutEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Paths;

import static java.nio.file.Files.newInputStream;
import static java.nio.file.Files.newOutputStream;
import static java.nio.file.StandardOpenOption.CREATE;

/**
 * Created by dimon on 05.05.14.
 */
@Service
public class AccountRepository {

    @Value("${accountInfo")
    private String path;

    public void save(AccountDto accountDto) {
        try (OutputStream outputStream = newOutputStream(Paths.get(path), CREATE);
             ObjectOutputStream objectWriter = new ObjectOutputStream(outputStream)){
            objectWriter.writeObject(accountDto);
        } catch (IOException e) {
            throw new AccountCannotBeSavedException();
        }
    }
    public AccountDto get() {
        try (InputStream inputStream = newInputStream(Paths.get(path));
             ObjectInputStream objectWriter = new ObjectInputStream(inputStream)) {
            return (AccountDto) objectWriter.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new AccountNotFoundException();
        }
    }

    public void remove() {
        File file = new File(path);
        file.delete();
    }
}
