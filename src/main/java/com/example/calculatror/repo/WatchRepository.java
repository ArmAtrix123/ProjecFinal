package com.example.calculatror.repo;

import com.example.calculatror.model.Watch;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WatchRepository extends CrudRepository<Watch,Long> {
    public List<Watch> findByName(String name);
    public List<Watch> findByNameContains(String name);
}
