package com.fly.house.ui.presenter;

import com.fly.house.io.operations.OperationHistory;
import com.fly.house.ui.view.HistoryView;
import com.fly.house.ui.view.ViewContainer;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.nio.file.Paths;
import java.util.Vector;

/**
 * Created by dimon on 3/8/14.
 */
@Component
public class HistoryPresenterImpl extends AbstractPresenter<HistoryView> implements HistoryPresenter {

    private OperationHistory history;

    private DefaultTableModel dataModel;

    @Autowired
    protected HistoryPresenterImpl(EventBus eventBus, HistoryView view,
                                   ViewContainer container, OperationHistory history) {
        super(eventBus, view, container);
        this.history = history;
    }

    @PostConstruct
    public void init() {
        JTable table = view.getTable();
        String[] header = view.getHeader();

        Object[][] data = new Object[header.length][3];

        for (int i = 0; i < 3; i++) {
            data[i][0] = Paths.get("/home/dimon/");
            data[i][1] = "DELETE";
            data[i][2] = Boolean.FALSE;
        }

        dataModel = new PathTableModel(data, header);
        table.setModel(dataModel);


    }

    @Override
    public void onApply() {
        Vector vector = dataModel.getDataVector();
        for (int i = 0; i < vector.size(); i++) {
            Vector vector1 = (Vector) vector.elementAt(i);
            Boolean valueAt = (Boolean) vector1.elementAt(2);
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
            if (columnIndex == 2) {
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
