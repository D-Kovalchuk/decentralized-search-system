package com.fly.house.model;

import org.hibernate.search.bridge.StringBridge;

/**
 * Created by dimon on 4/27/14.
 */
public class CategoryBridge implements StringBridge {

    @Override
    public String objectToString(Object object) {
        ArtifactCategory category = (ArtifactCategory) object;
        return category.toString();
    }
}
