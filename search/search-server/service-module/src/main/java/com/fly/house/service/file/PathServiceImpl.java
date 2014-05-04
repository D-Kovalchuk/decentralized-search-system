package com.fly.house.service.file;

import com.fly.house.dao.repository.PathRepository;
import com.fly.house.model.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dimon on 04.05.14.
 */
@Service
public class PathServiceImpl implements PathService {

    @Autowired
    private PathRepository pathRepository;

    @Override
    public Path save(Path path) {
        return pathRepository.save(path);
    }

    @Override
    public void delete(Long id) {
        pathRepository.delete(id);
    }

    @Override
    public List<Path> findByAccountName(String name) {
        return pathRepository.findByAccountName(name);
    }

}
