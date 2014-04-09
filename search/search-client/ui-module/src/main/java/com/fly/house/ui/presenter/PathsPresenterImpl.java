package com.fly.house.ui.presenter;

import com.fly.house.authentication.exception.AuthorizationException;
import com.fly.house.io.WatchServiceStorage;
import com.fly.house.io.exceptions.WatchServiceException;
import com.fly.house.ui.event.LogoutEvent;
import com.fly.house.ui.presenter.api.AbstractPresenter;
import com.fly.house.ui.qualifier.Presenter;
import com.fly.house.ui.view.PathsView;
import com.fly.house.ui.view.ViewContainer;
import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.table.DefaultTableModel;
import java.nio.file.Path;
import java.util.Set;
import java.util.Vector;


/**
 * Created by dimon on 3/8/14.
 */
@Presenter
public class PathsPresenterImpl extends AbstractPresenter<PathsView> implements PathsPresenter {

    private static final int EDITABLE_COLUMN = 1;
    private WatchServiceStorage storage;
    private DefaultTableModel dataModel;
    private static Logger logger = LoggerFactory.getLogger(PathsPresenterImpl.class);

    @Autowired
    public PathsPresenterImpl(EventBus eventBus, PathsView view,
                              ViewContainer container, WatchServiceStorage storage) {
        super(eventBus, view, container);
        this.storage = storage;
    }

    @Override
    public void init() {
        super.init();
        String[] header = view.getHeader();
        try {
            Set<Path> pathsSet = storage.asMap().keySet();
            Path[] paths = pathsSet.toArray(new Path[pathsSet.size()]);
            Object[][] data = new Object[pathsSet.size()][header.length];
            fillData(paths, data);

            dataModel = new TableModel(data, header, EDITABLE_COLUMN);
            view.getTable().setModel(dataModel);
        } catch (AuthorizationException ex) {
            logger.warn("exception occurred", ex);
            eventBus.post(new LogoutEvent());
        }

    }

    private void fillData(Path[] paths, Object[][] data) {
        for (int i = 0; i < paths.length; i++) {
            data[i][0] = paths[i];
            data[i][1] = Boolean.FALSE;
        }
    }

    @Override
    public void onUnregisterPath() {
        Vector rowVector = dataModel.getDataVector();
        try {
            for (int i = 0; i < rowVector.size(); i++) {
                Vector columnVector = (Vector) rowVector.elementAt(i);
                Boolean valueAt = (Boolean) columnVector.elementAt(1);
                if (valueAt) {
                    dataModel.removeRow(i);
                    Path path = (Path) columnVector.elementAt(0);
                    storage.unregister(path);
                }
            }
        } catch (AuthorizationException | WatchServiceException ex) {
            logger.warn("exception occurred", ex);
            eventBus.post(new LogoutEvent());
        }
    }

}
