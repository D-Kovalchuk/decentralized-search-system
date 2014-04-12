package com.fly.house.core.rest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Created by dimon on 2/27/14.
 */
public class CookieServiceTest {

    private Path path;
    private CookieService cookieService;

    @Before
    public void setUp() throws Exception {
        path = Paths.get("cookies").toAbsolutePath();
        path.toFile().mkdir();
        cookieService = new CookieService(path);
    }

    @After
    public void tearDown() throws Exception {
        File file = path.toFile();
        FileSystemUtils.deleteRecursively(file);
    }

    @Test
    public void saveCookieShouldSaveCookieOnDisk() {
        List<String> list = asList("fsf234fwewf2f");

        cookieService.saveCookie(list);

        assertThat(path.toFile().list().length, not(0));
    }

    @Test
    public void saveCookieShouldSaveCookieInMemory() {
        List<String> list = asList("fsf234fwewf2f");

        cookieService.saveCookie(list);

        assertThat(cookieService.isLoaded(), is(true));
    }

    @Test
    public void removeCookieShouldRemoveCookieFromMemory() throws IOException {
        path.resolve("fsf234fwewf2f").toFile().createNewFile();
        cookieService.removeCookies();

        assertThat(cookieService.isLoaded(), is(false));
    }

    @Test
    public void removeCookieShouldRemoveCookieFromDisk() throws IOException {
        path.resolve("fsf234fwewf2f").toFile().createNewFile();
        cookieService.removeCookies();

        assertThat(path.toFile().list().length, is(0));
    }

}
