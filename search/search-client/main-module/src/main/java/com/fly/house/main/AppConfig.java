package com.fly.house.main;

import com.fly.house.fileshare.config.FileShareConfig;
import com.fly.house.ui.config.UiConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by dimon on 3/28/14.
 */
@Configuration
@Import({FileShareConfig.class, UiConfig.class})
@ImportResource("classpath:spring-integration.xml")
public class AppConfig {
}
