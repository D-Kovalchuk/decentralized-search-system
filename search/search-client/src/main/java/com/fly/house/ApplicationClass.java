package com.fly.house;

import com.fly.house.ui.view.RootView;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by dimon on 1/26/14.
 */
public class ApplicationClass {

    public static void main(String[] args) throws IOException, InterruptedException {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(Config.class);
        appContext.registerShutdownHook();

        final RootView rootView = appContext.getBean(RootView.class);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                rootView.run();
            }
        });
    }
}
