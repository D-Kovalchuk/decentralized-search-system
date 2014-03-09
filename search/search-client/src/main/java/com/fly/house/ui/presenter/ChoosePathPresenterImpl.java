package com.fly.house.ui.presenter;

import com.fly.house.io.WatchServiceExecutor;
import com.fly.house.ui.view.ChoosePathView;
import com.fly.house.ui.view.ViewContainer;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JFileChooser.APPROVE_OPTION;

/**
 * Created by dimon on 3/7/14.
 */
@Component
public class ChoosePathPresenterImpl extends AbstractPresenter<ChoosePathView> implements ChoosePathPresenter {

    private WatchServiceExecutor executor;

    private List<Path> paths = new ArrayList<>();

    @Autowired
    protected ChoosePathPresenterImpl(EventBus eventBus, ChoosePathView view,
                                      ViewContainer container, WatchServiceExecutor executor) {
        super(eventBus, view, container);
        this.executor = executor;
    }

    @Override
    public void onAccept() {
        for (Path path : paths) {
            executor.createWatchService(path);
        }
    }

    @Override
    public void onPathsChosen() {
        JFileChooser fileChooser = view.getFileChooser();
        int returnVal = fileChooser.showOpenDialog(new JDialog());
        if (returnVal == APPROVE_OPTION) {
            String text = filesToString();
            JTextArea pathArea = view.getPathArea();
            pathArea.setText(text);
            paths = filesToPath();
        }
    }

    private List<Path> filesToPath() {
        JFileChooser fileChooser = view.getFileChooser();
        File[] files = fileChooser.getSelectedFiles();
        List<Path> pathList = new ArrayList<>();
        for (File file : files) {
            pathList.add(file.toPath());
        }
        return pathList;
    }

    private String filesToString() {
        StringBuilder sb = new StringBuilder();
        JFileChooser fileChooser = view.getFileChooser();
        File[] files = fileChooser.getSelectedFiles();
        for (File file : files) {
            sb.append(file);
            sb.append("\n");
        }
        return sb.toString();
    }

}
