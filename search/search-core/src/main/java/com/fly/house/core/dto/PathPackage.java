package com.fly.house.core.dto;

import java.io.Serializable;

/**
 * Created by dimon on 5/2/14.
 */
public class PathPackage implements Serializable {

    private AccountDto accountDto;

    private String path;

    private int port;

    public PathPackage(AccountDto accountDto, String path, int port) {
        this.accountDto = accountDto;
        this.path = path;
        this.port = port;
    }

    public PathPackage(AccountDto accountDto, String path) {
        this.accountDto = accountDto;
        this.path = path;
    }

    public int getPort() {
        return port;
    }

    public AccountDto getAccount() {
        return accountDto;
    }

    public String getPath() {
        return path;
    }
}
