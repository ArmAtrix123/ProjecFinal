package com.example.calculatror.repo;

import com.example.calculatror.model.MacBook;
import com.example.calculatror.model.onetomany.Sklad;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SkladRepository extends CrudRepository<Sklad,Long> {
    public List<Sklad> findByName(String name);
    public List<Sklad> findByNameContains(String name);
}
