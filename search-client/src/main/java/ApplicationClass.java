import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by dimon on 1/26/14.
 */
public class ApplicationClass {

    public static void main(String[] args) throws IOException, InterruptedException {
        // create infrastructure


        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("spring.xml");

        appContext.close();

//        WatchServiceExecutor bean = appContext.getBean(WatchServiceExecutor.class);
//
//        bean.createWatchService(Paths.get("/home/dimon/share"));
//        bean.createWatchService(Paths.get("/home/dimon/share1"));
//
//        Thread.sleep(3000);
//        WatchServiceStorage bean1 = appContext.getBean(WatchServiceStorage.class);
//        bean1.unregister(Paths.get("/home/dimon/share"));
//        bean1.unregister(Paths.get("/home/dimon/share1"));

    }

}
