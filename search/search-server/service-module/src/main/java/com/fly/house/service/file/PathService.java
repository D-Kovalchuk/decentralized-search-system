package com.fly.house.service.file;

import com.fly.house.model.Path;

import java.util.List;

/**
 * Created by dimon on 04.05.14.
 */
public interface PathService {

    Path save(Path path);

    void delete(Long id);

    List<Path> findByAccountName(String name);

}
