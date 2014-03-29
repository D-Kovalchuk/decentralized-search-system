package com.fly.house.io;

import com.fly.house.io.operations.api.OperationHistory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * Created by dimon on 3/12/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class WatchServiceExecutorTest {

    @Mock
    private WatchServiceStorage storage;

    @Mock
    private OperationHistory history;

    @Spy
    private FakeThreadPool fakeThreadPool = new FakeThreadPool();

    @InjectMocks
    private WatchServiceExecutor executor;

    private Path path = Paths.get("/home/domin");

    @Test
    public void initShouldAddNewChangesToHistory() throws Exception {
        Map<Path, WatchService> map = new HashMap<>();
        WatchService watchService = mock(WatchService.class);
        map.put(path, watchService);
        when(storage.asMap()).thenReturn(map);

        executor.init();

        verify(history).addChangesToHistory(path);
    }

    @Test
    public void initShouldExecuteExistedWatchServices() {
        Map<Path, WatchService> map = new HashMap<>();
        WatchService watchService = mock(WatchService.class);
        map.put(path, watchService);
        when(storage.asMap()).thenReturn(map);

        executor.init();

        verify(fakeThreadPool).submit(any(Runnable.class));
    }

    @Test
    public void createWatchServiceShouldAddNewChangesToHistory() {
        executor.createWatchService(path);

        verify(history).addChangesToHistory(path);
    }

    @Test
    public void createWatchServiceShouldRegisterPathInStorage() {
        executor.createWatchService(path);

        verify(storage).register(path);
    }

    @Test
    public void createWatchServiceShouldStartToWatchPath() {
        executor.createWatchService(path);

        verify(fakeThreadPool).submit(any(Runnable.class));
    }
}
