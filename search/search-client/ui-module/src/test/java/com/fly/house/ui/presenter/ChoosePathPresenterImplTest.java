package com.fly.house.ui.presenter;

import com.fly.house.io.WatchServiceExecutor;
import com.fly.house.ui.view.ChoosePathView;
import com.fly.house.ui.view.ViewContainer;
import com.google.common.eventbus.EventBus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Arrays.asList;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JFileChooser.CANCEL_OPTION;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by dimon on 3/10/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class ChoosePathPresenterImplTest {

    @Mock
    private EventBus eventBus;
    @Mock
    private ChoosePathView view;
    @Mock
    private ViewContainer container;
    @Mock
    private WatchServiceExecutor executor;
    @InjectMocks
    private ChoosePathPresenterImpl presenter;
    @Mock
    private JFileChooser fileChooser;
    @Mock
    private JTextArea textArea;

    @Test
    public void onAcceptShouldRegisterNewPaths() {
        Path path = Paths.get("/");
        Whitebox.setInternalState(presenter, "paths", asList(path));

        presenter.onAccept();

        verify(executor).createWatchService(path);
    }

    @Test
    public void onPathsChosenShouldGetFileChoose() {
        setUpMockView(CANCEL_OPTION);

        presenter.onPathsChosen();

        verify(view).getFileChooser();
    }

    @Test
    public void onPathsChosenShouldShowFileChooserInNewDialog() {
        setUpMockView(CANCEL_OPTION);

        presenter.onPathsChosen();

        verify(fileChooser).showOpenDialog(any(JDialog.class));
    }

    @Test
    public void onPathsChosenShouldShowChosenFiles() {
        setUpMockView(APPROVE_OPTION);

        presenter.onPathsChosen();

        verify(view).getPathArea();
        verify(textArea).setText("file\n");
    }

    private void setUpMockView(int option) {
        when(view.getFileChooser()).thenReturn(fileChooser);
        when(fileChooser.showOpenDialog(any(JDialog.class))).thenReturn(option);
        when(fileChooser.getSelectedFiles()).thenReturn(new File[]{new File("file")});
        when(view.getPathArea()).thenReturn(textArea);
    }


}
