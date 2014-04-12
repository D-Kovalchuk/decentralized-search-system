package com.fly.house.ui.view.api;

import com.fly.house.ui.presenter.api.Presenter;
import com.fly.house.ui.view.TopMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Created by dimon on 3/9/14.
 */
public interface View<P extends Presenter> {

    Logger logger = LoggerFactory.getLogger(View.class);

    JPanel asJPanel();

    void setPresenter(P presenter);

    TopMenu getTopMenu();

    default void cleanUp() {
        Field[] fields = this.getClass().getDeclaredFields();
        Arrays.stream(fields)
                .filter(field -> JTextComponent.class.isAssignableFrom(field.getType()))
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        JTextComponent obj = (JTextComponent) field.get(this);
                        obj.setText("");
                    } catch (IllegalAccessException e) {
                        logger.warn("while cleaning up exception was occurred", e);
                    }
                });
    }

}
