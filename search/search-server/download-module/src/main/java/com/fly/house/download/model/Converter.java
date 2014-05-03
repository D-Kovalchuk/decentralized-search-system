package com.fly.house.download.model;

import com.fly.house.core.dto.PathPackage;
import com.fly.house.download.exception.UserOfflineException;
import com.fly.house.model.Account;
import com.fly.house.registration.service.AccountService;
import com.fly.house.registration.service.OnlineService;
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
            //todo fix
            Account account = new Account()/*accountService.findAccountByName()*/;
            return new DownloadInfo(pathPackage, ip, account);
        } else {
            throw new UserOfflineException();
        }
    }

}