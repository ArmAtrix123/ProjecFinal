package com.example.calculatror.repo;

import com.example.calculatror.model.Country;
import com.example.calculatror.model.onetomany.Sklad;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CountryRepository extends CrudRepository<Country,Long> {
    public List<Country> findByName(String name);
    public List<Country> findByNameContains(String name);
}
