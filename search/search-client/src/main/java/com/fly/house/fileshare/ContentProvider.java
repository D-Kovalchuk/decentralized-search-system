package com.fly.house.fileshare;

import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * Created by dimon on 3/26/14.
 */
public class ContentProvider {

    private RandomAccessFile raf;

    public ContentProvider(File file) throws FileNotFoundException {
        raf = new RandomAccessFile(file, "r");
    }

    public long length() {
        try {
            return raf.length();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public FileChannel getChannel() {
        return raf.getChannel();
    }

    public FileRegion getRegion() {
        return new DefaultFileRegion(getChannel(), 0, length());
    }
}
