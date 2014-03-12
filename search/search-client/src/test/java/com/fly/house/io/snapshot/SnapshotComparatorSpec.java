package com.fly.house.io.snapshot;

import com.fly.house.io.event.Event;
import com.fly.house.io.event.EventBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.fly.house.io.event.EventType.CREATE;
import static com.fly.house.io.event.EventType.DELETE;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by dimon on 1/30/14.
 */
public class SnapshotComparatorSpec {

    private SnapshotComparator comparator;

    @Before
    public void setUp() {
        comparator = new SnapshotComparator();
    }

    @Test
    public void shouldReturnEmptyListOfEventsWhenSnapshotsAreEmpty() {
        List<Event> events = comparator.getDiff(new Snapshot(Collections.<File>emptyList()),
                new Snapshot(Collections.<File>emptyList()));

        assertThat(events.isEmpty(), is(true));
    }

    @Test
    public void shouldReturnListWithTwoCreateEventsWhenSteelSnapshotIsEmpty() {
        List<File> list = new ArrayList<>();
        final File file1 = new File("file1");
        list.add(file1);
        final File file2 = new File("file2");
        list.add(file2);
        Snapshot newSnapshot = new Snapshot(list);

        List<Event> events = comparator.getDiff(newSnapshot, new Snapshot(Collections.<File>emptyList()));

        assertThat(events.contains(new EventBuilder().type(CREATE).path(file1.toPath()).build()), is(true));
        assertThat(events.contains(new EventBuilder().type(CREATE).path(file2.toPath()).build()), is(true));
    }

    @Test
    public void shouldReturnListWithTwoCreateAndOneRemoveEvents() {
        List<File> list = new ArrayList<>();
        final File file1 = new File("file1");
        final File file3 = new File("file3");
        list.add(file3);
        list.add(file1);
        List<File> list2 = new ArrayList<>();
        final File file2 = new File("file2");
        list2.add(file2);
        Snapshot newSnapshot = new Snapshot(list);
        Snapshot oldSnapshot = new Snapshot(list2);

        List<Event> events = comparator.getDiff(newSnapshot, oldSnapshot);

        assertThat(events.contains(new EventBuilder().type(CREATE).path(file1.toPath()).build()), is(true));
        assertThat(events.contains(new EventBuilder().type(CREATE).path(file3.toPath()).build()), is(true));
        assertThat(events.contains(new EventBuilder().type(CREATE).path(file2.toPath()).build()), is(true));
    }


    @Test
    public void shouldReturnEmptyWithRemoveEvents() {
        List<File> list = new ArrayList<>();
        final File file1 = new File("file1");
        list.add(file1);
        final File file2 = new File("file2");
        list.add(file2);
        Snapshot oldSnapshot = new Snapshot(list);

        List<Event> events = comparator.getDiff(new Snapshot(Collections.<File>emptyList()), oldSnapshot);

        assertThat(events.contains(new EventBuilder().type(DELETE).path(file1.toPath()).build()), is(true));
        assertThat(events.contains(new EventBuilder().type(DELETE).path(file2.toPath()).build()), is(true));
    }

}
