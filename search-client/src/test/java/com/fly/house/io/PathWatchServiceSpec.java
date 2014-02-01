package com.fly.house.io;

import com.fly.house.io.operations.FileOperationHistory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.fly.house.io.EventType.*;
import static java.nio.file.StandardWatchEventKinds.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dimon on 1/29/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class PathWatchServiceSpec {

    @Mock
    private WatchService watchService;

    @Mock
    private FileOperationHistory operationHistory;

    private PathWatchService pathWatchService;

    @Before
    public void setUp() throws Exception {
        pathWatchService = new PathWatchService(watchService, operationHistory);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void startWatchShouldDoNothingWhenAnyEventsWereFired() throws InterruptedException {
        when(watchService.poll(5, TimeUnit.MINUTES)).thenReturn(null);

        pathWatchService.startWatch();

        verify(watchService).poll(5, TimeUnit.MINUTES);
        verifyNoMoreInteractions(watchService);
        verifyZeroInteractions(operationHistory);
    }

    @Test
    public void startWatchShouldStopWhenThrownClosedWatchServiceException() throws InterruptedException {
        when(watchService.poll(5, TimeUnit.MINUTES)).thenThrow(new ClosedWatchServiceException());

        pathWatchService.startWatch();

        verifyZeroInteractions(operationHistory);
        assertThat(pathWatchService.isStop(), is(true));
    }

    @Test
    public void startWatchShouldStopWhenThrownInterruptedException() throws InterruptedException {
        when(watchService.poll(5, TimeUnit.MINUTES)).thenThrow(new InterruptedException());

        pathWatchService.startWatch();

        verifyZeroInteractions(operationHistory);
        assertThat(pathWatchService.isStop(), is(true));
    }

    //todo test event assert for not null
    @Test
    public void encapsulateEventsShouldReturnNullWhenFiredNoEvents() {
        Event event = pathWatchService.encapsulateEvents(new ArrayList<WatchEvent<Path>>());

        assertThat(event.getType(), is(UNKNOWN));
    }

    @Test
    public void encapsulateEventsShouldReturnCreateEvent() {
        ArrayList<WatchEvent<Path>> events = createEvent(ENTRY_CREATE);

        Event event = pathWatchService.encapsulateEvents(events);

        assertThat(event.getType(), is(CREATE));
    }

    @Test
    public void encapsulateEventsShouldReturnCreateEventAndNewPathIsNotNull() {
        ArrayList<WatchEvent<Path>> events = createEvent(ENTRY_CREATE);

        Event event = pathWatchService.encapsulateEvents(events);

        assertNotNull(event.getNewPath());
        assertNull(event.getOldPath());
    }

    @Test
    public void encapsulateEventsShouldReturnDeleteEvent() {
        ArrayList<WatchEvent<Path>> events = createEvent(ENTRY_DELETE);

        Event event = pathWatchService.encapsulateEvents(events);

        assertThat(event.getType(), is(DELETE));
    }

    @Test
    public void encapsulateEventsShouldReturnDeleteEventAndOldPathIsNotNull() {
        ArrayList<WatchEvent<Path>> events = createEvent(ENTRY_DELETE);

        Event event = pathWatchService.encapsulateEvents(events);

        assertNotNull(event.getOldPath());
        assertNull(event.getNewPath());
    }

    @Test
    public void startWatchShouldReturnUnknownEventWhenFiredModifyEvent() {
        ArrayList<WatchEvent<Path>> events = createEvent(ENTRY_MODIFY);

        Event event = pathWatchService.encapsulateEvents(events);

        assertThat(event.getType(), is(UNKNOWN));
    }

    @Test
    public void encapsulateEventsShouldReturnModifyEvent() {
        ArrayList<WatchEvent<Path>> events = createEvent(ENTRY_CREATE, ENTRY_DELETE);

        Event event = pathWatchService.encapsulateEvents(events);

        assertThat(event.getType(), is(MODIFY));
    }

    @Test
    public void encapsulateEventsShouldReturnModifyEventAndNewPathAndOldPathAreNotNull() {
        ArrayList<WatchEvent<Path>> events = createEvent(ENTRY_CREATE, ENTRY_DELETE);

        Event event = pathWatchService.encapsulateEvents(events);

        assertNotNull(event.getNewPath());
        assertNotNull(event.getOldPath());
    }

    @Test
    public void startWatchShouldSaveCommandInOperationHistory() throws InterruptedException {
        WatchKey watchKey = mock(WatchKey.class);
        List<WatchEvent<?>> event = createEvent1(ENTRY_CREATE);

        when(watchKey.pollEvents()).thenReturn(event);
        when(watchService.poll(5, TimeUnit.MINUTES)).thenReturn(watchKey);

        Event event1 = Event.create(EventType.CREATE)
                .withNewPath(Paths.get("/"));

        pathWatchService.startWatch();

        verify(operationHistory).putCommand(event1);
        verifyNoMoreInteractions(operationHistory);
    }


    @SafeVarargs
    private final ArrayList<WatchEvent<Path>> createEvent(WatchEvent.Kind<Path>... kindsOfEvent) {
        ArrayList<WatchEvent<Path>> events = new ArrayList<>();
        for (WatchEvent.Kind<Path> kindOfEvent : kindsOfEvent) {
            WatchEvent<Path> event = mock(WatchEvent.class);
            when(event.count()).thenReturn(1);
            when(event.kind()).thenReturn(kindOfEvent);
            when(event.context()).thenReturn(Paths.get("/"));
            events.add(event);
        }
        return events;
    }

    @SafeVarargs
    private final ArrayList<WatchEvent<?>> createEvent1(WatchEvent.Kind<Path>... kindsOfEvent) {
        ArrayList<WatchEvent<?>> events = new ArrayList<>();
        for (WatchEvent.Kind<Path> kindOfEvent : kindsOfEvent) {
            WatchEvent event = mock(WatchEvent.class);
            when(event.count()).thenReturn(1);
            when(event.kind()).thenReturn(kindOfEvent);
            when(event.context()).thenReturn(Paths.get("/"));
            events.add(event);
        }
        return events;
    }


}
