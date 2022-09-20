package com.example.calculatror.repo;

import com.example.calculatror.model.Proizvoditel;
import com.example.calculatror.model.onetomany.Adress;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProizvoditelRepository extends CrudRepository<Proizvoditel, Long> {
    public List<Proizvoditel> findByName(String name);
    public List<Proizvoditel> findByNameContains(String name);
}
