package com.fly.house.fileshare;

import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.http.*;
import org.hamcrest.Matcher;

import static org.junit.Assert.assertThat;

/**
 * Created by dimon on 3/27/14.
 */
public class AbstractHttpHandlerTestCase {

    protected EmbeddedChannel embeddedChannel;

    public void setUp(SimpleChannelInboundHandler<FullHttpRequest> handler) {
        embeddedChannel = new EmbeddedChannel(handler);
    }

    protected void assertThatStatus(Matcher<HttpResponseStatus> statusMatcher) {
        FullHttpResponse httpResponse = (FullHttpResponse) embeddedChannel.readOutbound();
        assertThat(httpResponse.getStatus(), statusMatcher);
    }

    protected DefaultFullHttpRequest createRequest(HttpMethod method, String uri) {
        return new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, method, uri);
    }
}
