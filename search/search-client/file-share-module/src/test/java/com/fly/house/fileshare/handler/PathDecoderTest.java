package com.fly.house.fileshare.handler;

import com.fly.house.encrypt.PathEncryptors;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by dimon on 3/31/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class PathDecoderTest extends AbstractHttpHandlerTestCase {

    @Spy
    private PathEncryptors pathEncryptors = new PathEncryptors("ss", 2);

    @InjectMocks
    private PathDecoder pathDecoder;

    @Before
    public void setUp() throws Exception {
        super.setUp(pathDecoder);
    }

    @Test
    public void handlerShouldAttachDecodedPath() {
        String pathString = "/access/file.xml";
        String encodedUrl = encodeUrl(pathString);
        FullHttpRequest fullHttpRequest = createRequest(GET, encodedUrl);
        embeddedChannel.writeInbound(fullHttpRequest);

        Attribute<File> path = embeddedChannel.attr(AttributeKey.valueOf("decodedPath"));

        File decodedPath = path.get();
        assertThat(decodedPath.toString(), equalTo(pathString));
    }

    private String encodeUrl(String pathString) {
        Path path = Paths.get(pathString);
        String encodedPath = pathEncryptors.encode(path);
        return "/".concat(encodedPath);
    }

}
