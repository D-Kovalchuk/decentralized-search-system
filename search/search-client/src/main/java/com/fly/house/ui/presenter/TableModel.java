package com.fly.house.ui.presenter;

import javax.swing.table.DefaultTableModel;

/**
 * Created by dimon on 3/11/14.
 */
public class TableModel extends DefaultTableModel {

    private int editableColumn;

    public TableModel(Object[][] data, String[] header, int editableColumn) {
        super(data, header);
        this.editableColumn = editableColumn;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == editableColumn;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }
}
