package com.fly.house.io;

import com.fly.house.io.exceptions.NotDirectoryException;
import com.fly.house.io.exceptions.WatchServiceRegistrationException;
import com.fly.house.io.exceptions.WatchServiceUnregistrationException;
import com.fly.house.io.repositories.api.PathRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by dimon on 1/29/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class InMemoryWSStorageTest {

    @Mock
    private PathRepository pathRepository;
    private Map<Path, WatchService> map;
    private InMemoryWSStorage storage;

    @Before
    public void setUp() throws Exception {
        storage = new InMemoryWSStorage(pathRepository);
        map = new HashMap<>();
        Whitebox.setInternalState(storage, "storage", map);
    }


    @Test(expected = WatchServiceRegistrationException.class)
    public void registerShouldThrowExceptionWhenRepositoryCannotSavePath() {
        Path path = Paths.get("/some/path");
        doThrow(new NotDirectoryException()).when(pathRepository).add(path);

        storage.register(path);
        fail("exception should have been thrown");
    }

    @Test(expected = WatchServiceRegistrationException.class)
    public void registerShouldThrowExceptionOnRegistrationNewWatchServiceOnPath() throws IOException {
        Path path = mock(Path.class);
        doNothing().when(pathRepository).add(path);
        when(path.register(any(WatchService.class), eq(ENTRY_CREATE), eq(ENTRY_DELETE))).thenThrow(new IOException());

        storage.register(path);
        fail("exception should have been thrown");
    }

    @Test
    public void watchServiceStorageShouldContainRegisteredPath() throws IOException {
        Path path = mock(Path.class);
        doNothing().when(pathRepository).add(path);

        storage.register(path);

        assertThat(map.containsKey(path), is(true));
    }

    @Test(expected = WatchServiceUnregistrationException.class)
    public void unregisterShouldThrowExceptionWhenRepositoryCannotRemovePath() {
        Path path = Paths.get("/some/path");
        doThrow(new NotDirectoryException()).when(pathRepository).remove(path);

        storage.unregister(path);
        fail("exception should have been thrown");
    }

    @Test
    public void watchServiceStorageShouldNotContainUnregisteredPath() {
        Path path = mock(Path.class);
        WatchService watchService = mock(WatchService.class);
        doNothing().when(pathRepository).remove(path);
        map.put(path, watchService);

        storage.unregister(path);

        assertThat(map.containsKey(path), is(false));
    }

    @Test
    public void watchServiceStorageShouldCloseWatchServiceWhenPathWasRemoved() throws IOException {
        Path path = mock(Path.class);
        WatchService watchService = mock(WatchService.class);
        doNothing().when(pathRepository).remove(path);
        map.put(path, watchService);

        storage.unregister(path);

        verify(watchService).close();
    }


}
