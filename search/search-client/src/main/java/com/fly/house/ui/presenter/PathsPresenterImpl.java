package com.fly.house.ui.presenter;

import com.fly.house.io.WatchServiceStorage;
import com.fly.house.ui.presenter.api.AbstractPresenter;
import com.fly.house.ui.qualifier.Presenter;
import com.fly.house.ui.view.PathsView;
import com.fly.house.ui.view.ViewContainer;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
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

    @Autowired
    public PathsPresenterImpl(EventBus eventBus, PathsView view,
                              ViewContainer container, WatchServiceStorage storage) {
        super(eventBus, view, container);
        this.storage = storage;
    }

    @PostConstruct
    public void init() {
        String[] header = view.getHeader();

        Set<Path> pathsSet = storage.asMap().keySet();
        Path[] paths = pathsSet.toArray(new Path[pathsSet.size()]);
        Object[][] data = new Object[header.length][pathsSet.size()];
        fillData(paths, data);

        dataModel = new TableModel(data, header, EDITABLE_COLUMN);
        view.getTable().setModel(dataModel);
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
        for (int i = 0; i < rowVector.size(); i++) {
            Vector columnVector = (Vector) rowVector.elementAt(i);
            Boolean valueAt = (Boolean) columnVector.elementAt(1);
            if (valueAt) {
                dataModel.removeRow(i);
                Path path = (Path) columnVector.elementAt(0);
                storage.unregister(path);
            }
        }
    }
}
