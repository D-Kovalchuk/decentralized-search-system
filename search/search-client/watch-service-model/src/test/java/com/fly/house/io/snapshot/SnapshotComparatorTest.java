package com.fly.house.io.snapshot;

import com.fly.house.io.event.Event;
import com.fly.house.io.event.EventBuilder;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.fly.house.io.event.EventType.CREATE;
import static com.fly.house.io.event.EventType.DELETE;
import static java.util.Collections.emptyList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by dimon on 1/30/14.
 */
public class SnapshotComparatorTest {

    private SnapshotComparator comparator;

    @Before
    public void setUp() {
        comparator = new SnapshotComparator();
    }

    @Test
    public void shouldReturnEmptyListOfEventsWhenSnapshotsAreEmpty() {
        List<Event> events = comparator.getDiff(new Snapshot(emptyList()),
                new Snapshot(emptyList()));

        assertThat(events.isEmpty(), is(true));
    }

    @Test
    public void shouldReturnListWithTwoCreateEventsWhenSteelSnapshotIsEmpty() {
        List<Path> list = new ArrayList<>();
        Path file1 = Paths.get("file1");
        list.add(file1);
        Path file2 = Paths.get("file2");
        list.add(file2);
        Snapshot newSnapshot = new Snapshot(list);

        List<Event> events = comparator.getDiff(newSnapshot, new Snapshot(emptyList()));

        assertThat(events.contains(new EventBuilder().type(CREATE).path(file1).build()), is(true));
        assertThat(events.contains(new EventBuilder().type(CREATE).path(file2).build()), is(true));
    }

    @Test
    public void shouldReturnListWithTwoCreateAndOneRemoveEvents() {
        List<Path> list = new ArrayList<>();
        final Path file1 = Paths.get("file1");
        final Path file3 = Paths.get("file3");
        list.add(file3);
        list.add(file1);
        List<Path> list2 = new ArrayList<>();
        final Path file2 = Paths.get("file2");
        list2.add(file2);
        Snapshot newSnapshot = new Snapshot(list);
        Snapshot oldSnapshot = new Snapshot(list2);

        List<Event> events = comparator.getDiff(newSnapshot, oldSnapshot);

        assertThat(events.contains(new EventBuilder().type(CREATE).path(file1).build()), is(true));
        assertThat(events.contains(new EventBuilder().type(CREATE).path(file3).build()), is(true));
        assertThat(events.contains(new EventBuilder().type(DELETE).path(file2).build()), is(true));
    }


    @Test
    public void shouldReturnEmptyWithRemoveEvents() {
        List<Path> list = new ArrayList<>();
        final Path file1 = Paths.get("file1");
        list.add(file1);
        final Path file2 = Paths.get("file2");
        list.add(file2);
        Snapshot oldSnapshot = new Snapshot(list);

        List<Event> events = comparator.getDiff(new Snapshot(emptyList()), oldSnapshot);

        assertThat(events.contains(new EventBuilder().type(DELETE).path(file1).build()), is(true));
        assertThat(events.contains(new EventBuilder().type(DELETE).path(file2).build()), is(true));
    }

}
