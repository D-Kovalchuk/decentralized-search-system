package com.fly.house.core.encrypt;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Created by dimon on 3/28/14.
 */
public class PathEncryptorsTest {

    private PathEncryptors pathEncryptors;
    private Path path;

    @Before
    public void setUp() throws Exception {
        pathEncryptors = new PathEncryptors("salt", 10);
        path = Paths.get("/home/ddf/df");
    }

    @Test
    public void encodeShouldReturnNotNull() {
        String encodedPath = pathEncryptors.encode(path);
        assertNotNull(encodedPath);
    }

    @Test
    public void encodeShouldReturnNullWhenPathIsNull() {
        String encodedPath = pathEncryptors.encode(null);
        assertNull(encodedPath);
    }

    @Test
    public void decodeShouldReturnNullWhenSpecifiedNull() {
        Path decode = pathEncryptors.decode(null);

        assertNull(decode);
    }

    @Test
    public void decode() {
        String encodedPath = pathEncryptors.encode(path);
        Path decode = pathEncryptors.decode(encodedPath);
        assertThat(decode, equalTo(decode));
    }
}
