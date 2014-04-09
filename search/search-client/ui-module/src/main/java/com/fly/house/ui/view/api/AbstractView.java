package com.fly.house.ui.view.api;

import com.fly.house.ui.presenter.api.Presenter;
import com.fly.house.ui.view.TopMenu;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

/**
 * Created by dimon on 3/9/14.
 */
public abstract class AbstractView<P extends Presenter> extends JPanel implements View<P> {

    protected P presenter;

    @Autowired
    private TopMenu menu;

    protected AbstractView() {
    }

    protected AbstractView(LayoutManager layout) {
        super(layout);
    }

    protected AbstractView(int width, int height) {
        super(new BorderLayout());
        setPreferredSize(new Dimension(width, height));
    }

    @PostConstruct
    public void init() {
        menu.setVisible(true);
    }

    @Override
    public JPanel asJPanel() {
        return this;
    }

    @Override
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public TopMenu getTopMenu() {
        return menu;
    }
}
