package com.fly.house.ui.qualifier;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by dimon on 3/12/14.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Presenter {

    String value() default "";

}
