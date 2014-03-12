package com.fly.house.ui.presenter;

import com.fly.house.io.event.Event;
import com.fly.house.io.event.EventBuilder;
import com.fly.house.io.event.EventType;
import com.fly.house.io.operations.Command;
import com.fly.house.io.operations.OperationHistory;
import com.fly.house.ui.view.HistoryView;
import com.fly.house.ui.view.ViewContainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * Created by dimon on 3/11/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class HistoryPresenterImplTest {

    @Mock
    private HistoryView view;

    @Mock
    private ViewContainer container;

    @Mock
    private OperationHistory operationHistory;

    @InjectMocks
    private HistoryPresenterImpl presenter;

    @Test
    public void onShareShouldExecuteCommands() {
        Command command = mock(Command.class);
        Map<Event, Command> map = new HashMap<>();
        Event event = new EventBuilder().type(EventType.CREATE).path(Paths.get("/")).build();
        map.put(event, command);

        when(operationHistory.getHistory()).thenReturn(map);

        presenter.onShare();

        verify(command, atLeastOnce()).execute();
    }


}
