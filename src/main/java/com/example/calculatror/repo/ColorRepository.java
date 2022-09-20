package com.example.calculatror.repo;

import com.example.calculatror.model.Color;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ColorRepository extends CrudRepository<Color,Long> {
    public List<Color> findByName(String name);
    public List<Color> findByNameContains(String name);
}
