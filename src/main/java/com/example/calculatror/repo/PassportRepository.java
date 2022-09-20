package com.example.calculatror.repo;

import com.example.calculatror.model.MacBook;
import com.example.calculatror.model.Passport;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PassportRepository extends CrudRepository<Passport,Long> {
    public List<Passport> findBySeries(String series);
    public List<Passport> findBySeriesContains(String series);
}
