import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by dimon on 1/26/14.
 */
public class ApplicationClass {

    public static void main(String[] args) throws IOException, InterruptedException {
        // create infrastructure
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("spring.xml");

        appContext.registerShutdownHook();
        appContext.close();
    }

}
