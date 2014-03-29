package com.fly.house.fileshare;

import com.fly.house.fileshare.handler.FileAccessHandler;
import com.fly.house.fileshare.handler.util.PathService;
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

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static java.lang.String.join;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by dimon on 3/27/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class FileAccessHandlerTest extends AbstractHttpHandlerTestCase {

    public static final String RIGHT_PATH = "/search-client/src/test/resources";

    @Mock
    private PathService pathService;

    @InjectMocks
    private FileAccessHandler fileAccessHandler;

    @Before
    public void setUp() throws Exception {
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
        when(pathService.isPathTracked(any(Path.class))).thenReturn(true);

        embeddedChannel.writeInbound(fullHttpRequest);

        assertThatStatus(is(not(FORBIDDEN)));
    }

    @Test
    public void fileAccessShouldThrowNotFoundStatusWhenFileDoesNotExist() {
        String pathToFile = join("/", RIGHT_PATH, "file.html");
        FullHttpRequest fullHttpRequest = createRequest(GET, pathToFile);
        when(pathService.isPathTracked(any(Path.class))).thenReturn(true);

        embeddedChannel.writeInbound(fullHttpRequest);

        assertThatStatus(is(NOT_FOUND));
    }

    @Ignore
    @Test
    public void fileAccessShouldPassWhenDirExist() throws IOException {
        String pathToFile = join("/", RIGHT_PATH, "fileop");
        File file = new File(pathToFile);
        file.mkdir();
        FullHttpRequest fullHttpRequest = createRequest(GET, pathToFile);

        embeddedChannel.writeInbound(fullHttpRequest);

        assertThatStatus(is(not(NOT_FOUND)));

        file.delete();
    }

    //TODO check next if statement
    //TODO fix this when as an argument will be passed hash of a file path
}
