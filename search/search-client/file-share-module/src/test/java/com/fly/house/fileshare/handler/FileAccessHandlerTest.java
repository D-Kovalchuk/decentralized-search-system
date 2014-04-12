package com.fly.house.fileshare.handler;

import com.fly.house.fileshare.integration.PathService;
import io.netty.handler.codec.http.FullHttpRequest;
import org.junit.Before;
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
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by dimon on 3/27/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class FileAccessHandlerTest extends AbstractHttpHandlerTestCase {

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
        attachFileWithName("/unknown/path");

        embeddedChannel.writeInbound(fullHttpRequest);

        assertThatStatus(is(FORBIDDEN));
    }

    @Test
    public void fileAccessShouldPassWhenWasSpecifiedPathThatWasRegistered() throws IOException {
        FullHttpRequest fullHttpRequest = createRequest(GET, "file.html");
        attachFileWithName("file.html");

        when(pathService.isPathTracked(any(Path.class))).thenReturn(true);

        embeddedChannel.writeInbound(fullHttpRequest);

        assertThatStatus(is(not(FORBIDDEN)));
    }


    @Test
    public void fileAccessShouldThrowNotFoundStatusWhenFileDoesNotExist() {
        FullHttpRequest fullHttpRequest = createRequest(GET, "file.html");
        when(pathService.isPathTracked(any(Path.class))).thenReturn(true);
        attachFileWithName("file.html");

        embeddedChannel.writeInbound(fullHttpRequest);

        assertThatStatus(is(NOT_FOUND));
    }

    @Test
    public void fileAccessShouldPassWhenDirExist() throws IOException {
        String dir = "access/fileop";
        FullHttpRequest fullHttpRequest = createRequest(GET, dir);
        File file = new File(dir);
        file.mkdir();
        attachFileWithName(dir);

        embeddedChannel.writeInbound(fullHttpRequest);

        assertThatStatus(is(not(NOT_FOUND)));

        file.delete();
    }

    //TODO check next if statement
    //TODO fix this when as an argument will be passed hash of a file path
}
