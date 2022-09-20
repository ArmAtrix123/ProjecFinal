package com.example.calculatror.repo;

import com.example.calculatror.model.MacBook;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MacBookRepository extends CrudRepository<MacBook,Long> {
    public List<MacBook> findByTitle(String title);
    public List<MacBook> findByTitleContains(String title);
}
