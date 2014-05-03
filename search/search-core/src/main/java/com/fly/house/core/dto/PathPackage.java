package com.fly.house.core.dto;

import java.io.Serializable;

/**
 * Created by dimon on 5/2/14.
 */
public class PathPackage implements Serializable {

    private String name;

    private String path;

    private int port;

    public PathPackage(String name, String path, int port) {
        this.name = name;
        this.path = path;
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
