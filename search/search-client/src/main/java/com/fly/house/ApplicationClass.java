package com.fly.house;

import com.fly.house.ui.view.RootView;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * Created by dimon on 1/26/14.
 */
public class ApplicationClass {

    public static void main(String[] args) throws IOException, InterruptedException {
        // create infrastructure
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(Config.class);
        final RootView rootView = appContext.getBean(RootView.class);

        rootView.run();

//        appContext.registerShutdownHook();
//        appContext.close();
    }

}
