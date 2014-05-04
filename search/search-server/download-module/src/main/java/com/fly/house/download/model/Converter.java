package com.fly.house.download.model;

import com.fly.house.core.dto.PathPackage;
import com.fly.house.download.exception.UserOfflineException;
import com.fly.house.model.Account;
import com.fly.house.service.registration.AccountService;
import com.fly.house.service.registration.OnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * Created by dimon on 03.05.14.
 */
@Component
public class Converter {

    @Autowired
    private OnlineService onlineService;

    @Autowired
    private AccountService accountService;

    public DownloadInfo convert(PathPackage pathPackage) {
        String name = pathPackage.getName();
        if (onlineService.isOnline(name)) {
            InetAddress ip = onlineService.getAddress(name);
            Account account = accountService.findAccountByName(name);
            return new DownloadInfo(pathPackage, ip, account);
        } else {
            throw new UserOfflineException();
        }
    }

}
