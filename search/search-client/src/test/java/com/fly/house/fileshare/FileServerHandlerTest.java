//package com.fly.house.fileshare;
//
//import io.netty.channel.DefaultFileRegion;
//import io.netty.channel.embedded.EmbeddedChannel;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.IOException;
//
//import static com.fly.house.fileshare.FileMessage.FILE_NOT_FOUND;
//import static org.hamcrest.core.Is.is;
//import static org.hamcrest.core.IsNot.not;
//import static org.junit.Assert.assertThat;
//
///**
// * Created by dimon on 3/26/14.
// */
//public class FileServerHandlerTest {
//
//    @Test
//    public void fileServerHandlerWhenFileWithSomeContent() throws IOException {
//        EmbeddedChannel channel = new EmbeddedChannel(new FileServerHandler());
//        channel.writeInbound(new File("search-client/src/test/resources/sharedFile"));
//
//        DefaultFileRegion x = (DefaultFileRegion) channel.readOutbound();
//
//        assertThat(x.count(), is(not(0L)));
//    }
//
//    @Test
//    public void fileServerHandlerWhenFileDoesNotExists() {
//        EmbeddedChannel channel = new EmbeddedChannel(new FileServerHandler());
//        channel.writeInbound(new File("path/to/file"));
//
//        FileMessage fileMessage = (FileMessage) channel.readOutbound();
//
//        assertThat(fileMessage, is(FILE_NOT_FOUND));
//    }
//
//
//
//
//}
