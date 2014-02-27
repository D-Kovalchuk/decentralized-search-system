package com.fly.house.io;

import com.fly.house.io.event.Event;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.fly.house.io.event.EventType.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by dimon on 2/27/14.
 */
public class EventTest {

    Path path = Paths.get("some/path");
    Event unknownEvent = Event.create(UNKNOWN);
    Event createEvent = Event.create(CREATE);
    Event deleteEvent = Event.create(DELETE);

    @Test
    public void createUnknownEventShouldHaveTypeUNKNOWN() {
        assertThat(unknownEvent.getType(), equalTo(UNKNOWN));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createUnknownEventShouldThrowExceptionOnWithNewPathMethodCall() {
        unknownEvent.withNewPath(path);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createUnknownEventShouldThrowExceptionOnWithOldPathMethodCall() {
        unknownEvent.withOldPath(path);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createUnknownEventShouldThrowExceptionOnGetOldPathMethodCall() {
        unknownEvent.getOldPath();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createUnknownEventShouldThrowExceptionOnGetNewPathMethodCall() {
        unknownEvent.getNewPath();
    }

    @Test
    public void createCreateEventShouldHaveTypeCREATE() {
        assertThat(createEvent.getType(), equalTo(CREATE));
    }

    @Test
    public void createCreateEventShouldHaveNewPath() {
        createEvent.withNewPath(path);
        assertThat(createEvent.getNewPath(), equalTo(path));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createCreateEventShouldThrowExceptionWhenCallWithOldPath() {
        createEvent.withOldPath(path);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createCreateEventShouldThrowExceptionWhenCallGetOldPath() {
        createEvent.getOldPath();
    }

    @Test
    public void createDeleteEventShouldHaveTypeDELETE() {
        assertThat(deleteEvent.getType(), equalTo(DELETE));
    }

    @Test
    public void createDeleteEventShouldHaveOldPath() {
        deleteEvent.withOldPath(path);
        assertThat(deleteEvent.getOldPath(), equalTo(path));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createDeleteEventShouldThrowExceptionWhenMethodWithNewPathWasCalled() {
        deleteEvent.withNewPath(path);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createDeleteEventShouldThrowExceptionWhenMethodGetNewPathWasCalled() {
        deleteEvent.getNewPath();
    }


}
