package com.fly.house.download.model;

import com.fly.house.core.dto.AccountDto;
import com.fly.house.core.dto.PathPackage;
import com.fly.house.download.exception.UserOfflineException;
import com.fly.house.model.Account;
import com.fly.house.service.registration.AccountService;
import com.fly.house.service.registration.OnlineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(Converter.class);

    public DownloadInfo convert(PathPackage pathPackage) {
        AccountDto accountDto = pathPackage.getAccount();
        String name = accountDto.getLogin();
        if (onlineService.isOnline(name)) {
            logger.debug("User online");
            InetAddress ip = onlineService.getAddress(name);
            Account account = accountService.findAccountById(accountDto.getId());
            DownloadInfo downloadInfo = new DownloadInfo(pathPackage, ip, account);
            logger.debug("PathPackage={} converted into DownloadInfo={}", pathPackage, downloadInfo);
            return downloadInfo;
        } else {
            logger.debug("User offline");
            throw new UserOfflineException("User offline");
        }
    }

}
