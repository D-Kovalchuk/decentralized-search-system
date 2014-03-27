package com.fly.house.fileshare;

import com.fly.house.io.WatchServiceStorage;
import io.netty.handler.codec.http.FullHttpRequest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchService;
import java.util.HashMap;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static java.lang.String.join;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by dimon on 3/27/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class FileAccessHandlerTest extends AbstractHttpHandlerTestCase {

    public static final String RIGHT_PATH = "/search-client/src/test/resources";

    @Mock
    private WatchServiceStorage mock;

    @InjectMocks
    private FileAccessHandler fileAccessHandler;

    @Before
    public void setUp() throws Exception {
        HashMap<Path, WatchService> map = new HashMap<>();
        map.put(Paths.get(RIGHT_PATH), mock(WatchService.class));
        when(mock.asMap()).thenReturn(map);

        super.setUp(fileAccessHandler);
    }

    @Test
    public void fileAccessShouldThrowForbiddenStatusWhenPathDoesNotRegistered() throws IOException {
        FullHttpRequest fullHttpRequest = createRequest(GET, "/unknown/path");

        embeddedChannel.writeInbound(fullHttpRequest);

        assertThatStatus(is(FORBIDDEN));
    }

    @Test
    public void fileAccessShouldPassWhenWasSpecifiedPathThatWasRegistered() throws IOException {
        String pathToFile = join("/", RIGHT_PATH, "file.html");
        FullHttpRequest fullHttpRequest = createRequest(GET, pathToFile);

        embeddedChannel.writeInbound(fullHttpRequest);

        assertThatStatus(is(not(FORBIDDEN)));
    }

    @Test
    public void fileAccessShouldThrowNotFoundStatusWhenFileDoesNotExist() {
        String pathToFile = join("/", RIGHT_PATH, "file.html");
        FullHttpRequest fullHttpRequest = createRequest(GET, pathToFile);

        embeddedChannel.writeInbound(fullHttpRequest);

        assertThatStatus(is(NOT_FOUND));
    }

    @Ignore
    @Test
    public void fileAccessShouldPassWhenFileExist() throws IOException {
        String pathToFile = join("/", RIGHT_PATH, "fileop.html");
        File file = new File(pathToFile.substring(1));
        file.createNewFile();
        FullHttpRequest fullHttpRequest = createRequest(GET, pathToFile);

        embeddedChannel.writeInbound(fullHttpRequest);

        assertThatStatus(is(not(NOT_FOUND)));

        file.delete();
    }

    //TODO check next if statement

}
