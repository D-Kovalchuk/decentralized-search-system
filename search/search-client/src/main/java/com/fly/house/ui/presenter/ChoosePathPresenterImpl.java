package com.fly.house.ui.presenter;

import com.fly.house.io.WatchServiceExecutor;
import com.fly.house.ui.presenter.api.AbstractPresenter;
import com.fly.house.ui.qualifier.Presenter;
import com.fly.house.ui.view.ChoosePathView;
import com.fly.house.ui.view.ViewContainer;
import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JFileChooser.APPROVE_OPTION;

/**
 * Created by dimon on 3/7/14.
 */
@Presenter
public class ChoosePathPresenterImpl extends AbstractPresenter<ChoosePathView> implements ChoosePathPresenter {

    private WatchServiceExecutor executor;
    private List<Path> paths = new ArrayList<>();
    private static Logger logger = LoggerFactory.getLogger(ChoosePathPresenterImpl.class);

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
        logger.debug("Show JFileChooser in new dialog window");
        if (returnVal == APPROVE_OPTION) {
            logger.debug("User approve chosen files");
            File[] files = fileChooser.getSelectedFiles();
            String text = filesToString(files);
            JTextArea pathArea = view.getPathArea();
            pathArea.setText(text);
            paths = filesToPath(files);
        } else {
            logger.debug("User close dialog");
        }
    }

    private List<Path> filesToPath(File[] files) {
        logger.debug("transform file array to list of paths");
        List<Path> pathList = new ArrayList<>();
        for (File file : files) {
            pathList.add(file.toPath());
        }
        return pathList;
    }

    private String filesToString(File[] files) {
        logger.debug("transform file array to string");
        StringBuilder sb = new StringBuilder();
        for (File file : files) {
            sb.append(file);
            sb.append("\n");
        }
        return sb.toString();
    }

}
