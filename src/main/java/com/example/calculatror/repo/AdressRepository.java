package com.example.calculatror.repo;

import com.example.calculatror.model.MacBook;
import com.example.calculatror.model.onetomany.Adress;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdressRepository extends CrudRepository<Adress,Long> {
    public List<Adress> findByStreet(String street);
    public List<Adress> findByStreetContains(String street);

}
