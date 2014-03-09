package com.fly.house.ui.presenter;

import com.fly.house.io.WatchServiceStorage;
import com.fly.house.ui.view.PathsView;
import com.fly.house.ui.view.ViewContainer;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.Vector;


/**
 * Created by dimon on 3/8/14.
 */
@Component
public class PathsPresenterImpl extends AbstractPresenter<PathsView> implements PathsPresenter {

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
        JTable table = view.getTable();
        String[] header = view.getHeader();

        Set<Path> pathsSet = storage.asMap().keySet();
        Path[] paths = pathsSet.toArray(new Path[pathsSet.size()]);
        Object[][] data = new Object[header.length][2];


        for (int i = 0; i < 2; i++) {
            data[i][0] = Paths.get("/");
            data[i][1] = Boolean.FALSE;
        }

        dataModel = new PathTableModel(data, header);
        table.setModel(dataModel);


    }

    @Override
    public void onUnregisterPath() {
        Vector vector = dataModel.getDataVector();
        for (int i = 0; i < vector.size(); i++) {
            Vector vector1 = (Vector) vector.elementAt(i);
            Boolean valueAt = (Boolean) vector1.elementAt(1);
            if (valueAt) {
                dataModel.removeRow(i);
            }
        }
    }


    private class PathTableModel extends DefaultTableModel {

        private PathTableModel(Object[][] data, String[] header) {
            super(data, header);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (columnIndex == 1) {
                return true;
            }
            return false;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return getValueAt(0, columnIndex).getClass();
        }

    }

}
