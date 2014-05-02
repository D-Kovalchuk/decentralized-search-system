package com.fly.house.core.dto;

/**
 * Created by dimon on 5/2/14.
 */
public class PathPackage {

    private String name;

    private String path;

    public PathPackage(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
