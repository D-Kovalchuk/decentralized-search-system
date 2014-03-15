package com.fly.house.io.repositories;

import com.fly.house.io.repositories.api.AbstractPathRepository;
import com.fly.house.io.repositories.api.PathRepo;
import com.fly.house.io.repositories.api.RemoteRepo;

import java.nio.file.Path;
import java.util.List;

/**
 * Created by dimon on 1/29/14.
 */
@RemoteRepo
@PathRepo
public class RemotePathRepository extends AbstractPathRepository {

    @Override
    public void add(Path path) {
        super.add(path);
    }

    @Override
    public void remove(Path path) {
        super.remove(path);
    }

    @Override
    public List<Path> getPaths() {
        return null;
    }


    //todo implement this class
}
