package com.fly.house.download.service;

import com.fly.house.core.dto.PathPackage;
import org.springframework.integration.annotation.Gateway;

/**
 * Created by dimon on 5/2/14.
 */
public interface DownloadService {

    @Gateway
    public PathPackage retrivePath();

}
