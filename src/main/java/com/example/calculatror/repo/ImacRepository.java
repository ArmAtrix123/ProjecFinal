package com.example.calculatror.repo;

import com.example.calculatror.model.Imac;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ImacRepository extends CrudRepository<Imac,Long> {
    public List<Imac> findByName(String name);
    public List<Imac> findByNameContains(String name);
}
