package com.fly.house.download.model;

import com.fly.house.core.dto.PathPackage;
import com.fly.house.model.Account;

import java.net.InetAddress;

/**
 * Created by dimon on 03.05.14.
 */
public class DownloadInfo {

    private Account account;
    private InetAddress ip;
    private String path;
    private int port;

    public DownloadInfo(PathPackage pathPackage, InetAddress ip, Account account) {
        path = pathPackage.getPath();
        port = pathPackage.getPort();
        this.ip = ip;
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public InetAddress getIp() {
        return ip;
    }

    public String getPath() {
        return path;
    }

    public int getPort() {
        return port;
    }
}
