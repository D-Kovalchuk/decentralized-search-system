package com.fly.house.download;

import com.fly.house.download.config.DownloadConfig;
import com.fly.house.download.service.DownloadServiceFacade;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by dimon on 03.05.14.
 */
public class PathInfoMessageListener {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(DownloadConfig.class);
        appContext.registerShutdownHook();

        DownloadServiceFacade serviceFacade = appContext.getBean(DownloadServiceFacade.class);
        while (true) {
            try {
                serviceFacade.receive();
            } catch (InterruptedException e) {
                break;
            }
        }
    }

}
