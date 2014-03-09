import com.fly.house.ui.view.RootView;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by dimon on 1/26/14.
 */
public class ApplicationClass {

    public static void main(String[] args) throws IOException, InterruptedException {
        // create infrastructure
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("spring.xml");
//        final TopMenu menu = appContext.getBean(TopMenu.class);
//        System.out.println(menu);
        final RootView rootView = appContext.getBean(RootView.class);


//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
        rootView.run();
//            }
//        });


//        appContext.registerShutdownHook();
//        appContext.close();
    }

}
