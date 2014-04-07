package com.fly.house.main;

import com.fly.house.fileshare.FileShareServer;
import com.fly.house.fileshare.FileShareServerImpl;
import com.fly.house.ui.view.RootView;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;

/**
 * Created by dimon on 1/26/14.
 */
public class ApplicationClass {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);
        appContext.registerShutdownHook();

        final RootView rootView = appContext.getBean(RootView.class);
        SwingUtilities.invokeLater(rootView::run);

        FileShareServer fileShareServer = appContext.getBean(FileShareServerImpl.class);
        fileShareServer.start();
    }
}
