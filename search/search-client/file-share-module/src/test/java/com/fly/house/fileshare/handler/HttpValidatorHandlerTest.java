package com.fly.house.fileshare.handler;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import org.junit.Before;
import org.junit.Test;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpMethod.POST;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.METHOD_NOT_ALLOWED;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by dimon on 3/27/14.
 */
public class HttpValidatorHandlerTest extends AbstractHttpHandlerTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp(new HttpValidatorHandler());
    }

    @Test
    public void httpValidatorShouldThrowBadRequestStatusWhenRequestHasNotBeanDecodedYet() {
        DefaultFullHttpRequest request = createRequest(POST, "/");
        DefaultFullHttpRequest spyRequest = spy(request);
        DecoderResult decoderResult = mock(DecoderResult.class);
        when(spyRequest.getDecoderResult()).thenReturn(decoderResult);
        when(decoderResult.isSuccess()).thenReturn(false);

        embeddedChannel.writeInbound(spyRequest);

        assertThatStatus(is(BAD_REQUEST));
    }

    @Test
    public void httpValidatorShouldThrowMethodNotAllowWhenAccessMethodWasNotGet() {
        DefaultFullHttpRequest request = createRequest(POST, "/");

        embeddedChannel.writeInbound(request);

        assertThatStatus(is(METHOD_NOT_ALLOWED));
    }


    @Test
    public void httpValidatorShouldPassWhenAccessMethodWasGet() {
        DefaultFullHttpRequest request = createRequest(GET, "/home");

        embeddedChannel.writeInbound(request);


        Object obj = embeddedChannel.readOutbound();
        assertThat(obj, is((Object) null));
    }


}
