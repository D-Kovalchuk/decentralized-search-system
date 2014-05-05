package com.fly.house.service.converter;

import com.fly.house.core.dto.AccountDto;
import com.fly.house.core.dto.PathPackage;
import com.fly.house.model.Account;
import com.fly.house.model.File;

import static com.fly.house.service.converter.ConverterFactory.getAccountConverter;

/**
 * Created by dimon on 05.05.14.
 */
public class FileConverter implements Converter<File, PathPackage> {
    @Override
    public PathPackage convert(File entity) {
        Account account = entity.getAccount();
        AccountDto accountDto = getAccountConverter().convert(account);
        return new PathPackage(accountDto, entity.getPath(), 8980);
    }
}
