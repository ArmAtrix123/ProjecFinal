package com.example.calculatror.repo;

import com.example.calculatror.model.Iphone;
import com.example.calculatror.model.MacBook;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IphoneRepository extends CrudRepository<Iphone,Long> {
    public List<Iphone> findByTitle(String title);
    public List<Iphone> findByTitleContains(String title);
}
