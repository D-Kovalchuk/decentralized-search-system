package com.fly.house.download.event;

import com.fly.house.download.service.DownloadServiceFacade;
import com.fly.house.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by dimon on 03.05.14.
 */
@Component
public class FileDownloadCompleteEventHandler implements ApplicationListener<FileDownloadCompleteEvent> {

    @Autowired
    private DownloadServiceFacade downloadServiceFacade;

    @Override
    public void onApplicationEvent(FileDownloadCompleteEvent event) {
        File file = (File) event.getSource();
        downloadServiceFacade.send(file);
    }

}
