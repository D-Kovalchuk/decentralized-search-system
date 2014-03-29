package com.fly.house.io.event;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.util.ArrayList;
import java.util.List;

import static com.fly.house.io.event.EventType.*;
import static java.nio.file.StandardWatchEventKinds.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by dimon on 2/27/14.
 */
public class EventManagerTest {

    private EventManager eventManager;

    @Before
    public void setUp() throws Exception {
        eventManager = new EventManager();
    }

    @Test
    public void encapsulateEventsShouldReturnNullWhenFiredNoEvents() {
        List<Event> event = eventManager.encapsulateEvents(new ArrayList<WatchEvent<Path>>());

        assertThat(event.get(0).getType(), is(UNKNOWN));
    }

    @Test
    public void encapsulateEventsShouldReturnCreateEvent() {
        ArrayList<WatchEvent<Path>> events = createEvent(ENTRY_CREATE);

        List<Event> event = eventManager.encapsulateEvents(events);

        assertThat(event.get(0).getType(), is(CREATE));
    }

    @Test
    public void encapsulateEventsShouldReturnCreateEventAndNewPathIsNotNull() {
        ArrayList<WatchEvent<Path>> events = createEvent(ENTRY_CREATE);

        List<Event> event = eventManager.encapsulateEvents(events);

        assertNotNull(event.get(0).getPath());
    }

    @Test
    public void encapsulateEventsShouldReturnDeleteEvent() {
        ArrayList<WatchEvent<Path>> events = createEvent(ENTRY_DELETE);

        List<Event> event = eventManager.encapsulateEvents(events);

        assertThat(event.get(0).getType(), is(DELETE));
    }

    @Test
    public void encapsulateEventsShouldReturnDeleteEventAndOldPathIsNotNull() {
        ArrayList<WatchEvent<Path>> events = createEvent(ENTRY_DELETE);

        List<Event> event = eventManager.encapsulateEvents(events);

        assertNotNull(event.get(0).getPath());
    }

    @Test
    public void encapsulateEventsShouldReturnUnknownEventWhenPassedModifyEvent() {
        ArrayList<WatchEvent<Path>> events = createEvent(ENTRY_MODIFY);

        List<Event> event = eventManager.encapsulateEvents(events);

        assertThat(event.get(0).getType(), is(UNKNOWN));
    }

    @Test
    public void encapsulateEventsShouldReturnTwoEvent() {
        ArrayList<WatchEvent<Path>> events = createEvent(ENTRY_CREATE, ENTRY_DELETE);

        List<Event> event = eventManager.encapsulateEvents(events);

        assertThat(event.size(), is(2));
    }

    @Test
    public void encapsulateEventsShouldReturnModifyEventAndNewPathAndOldPathAreNotNull() {
        ArrayList<WatchEvent<Path>> events = createEvent(ENTRY_CREATE, ENTRY_DELETE);

        List<Event> event = eventManager.encapsulateEvents(events);

        assertNotNull(event.get(0).getPath());
        assertNotNull(event.get(1).getPath());
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
}
