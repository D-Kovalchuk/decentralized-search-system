package com.fly.house.service.converter;

/**
 * Created by dimon on 05.05.14.
 */
public class ConverterFactory {

    public static AccountConverter getAccountConverter() {
        return new AccountConverter();
    }

    public static FileConverter getFileConverter() {
        return new FileConverter();
    }

}
