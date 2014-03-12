package com.fly.house.ui.presenter;

import com.fly.house.io.event.Event;
import com.fly.house.io.operations.api.Command;
import com.fly.house.io.operations.api.OperationHistory;
import com.fly.house.ui.presenter.api.AbstractPresenter;
import com.fly.house.ui.qualifier.Presenter;
import com.fly.house.ui.view.HistoryView;
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
public class HistoryPresenterImpl extends AbstractPresenter<HistoryView> implements HistoryPresenter {

    private static final int EDITABLE_COLUMN = 2;
    private DefaultTableModel dataModel;
    private OperationHistory history;

    @Autowired
    public HistoryPresenterImpl(EventBus eventBus, HistoryView view,
                                ViewContainer container, OperationHistory history) {
        super(eventBus, view, container);
        this.history = history;
    }

    @PostConstruct
    public void init() {
        String[] header = view.getHeader();
        Object[][] data = new Object[header.length][history.getHistory().size()];
        fillData(data);
        dataModel = new TableModel(data, header, EDITABLE_COLUMN);
        view.getTable().setModel(dataModel);
    }

    @Override
    public void onApply() {
        Vector rowVector = dataModel.getDataVector();
        for (int i = 0; i < rowVector.size(); i++) {
            Vector columnVector = (Vector) rowVector.elementAt(i);
            Boolean valueAt = (Boolean) columnVector.elementAt(2);
            if (valueAt) {
                dataModel.removeRow(i);
                Path path = (Path) columnVector.elementAt(0);
                removeFormHistory(path);
            }
        }
    }

    @Override
    public void onShare() {
        for (Command command : history.getHistory().values()) {
            command.execute();
        }
    }

    private void fillData(Object[][] data) {
        Set<Event> events = history.getHistory().keySet();
        Event[] eventsArray = events.toArray(new Event[events.size()]);
        for (int i = 0; i < eventsArray.length; i++) {
            data[i][0] = eventsArray[i].getPath();
            data[i][1] = eventsArray[i].getType();
            data[i][2] = Boolean.FALSE;
        }
    }

    private void removeFormHistory(Path path) {
        Event eventToDelete = null;
        for (Event event : history.getHistory().keySet()) {
            if (event.getPath() == path) {
                eventToDelete = event;
                break;
            }
        }
        history.getHistory().remove(eventToDelete);
    }


}
