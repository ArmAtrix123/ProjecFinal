package com.example.calculatror.repo;

import com.example.calculatror.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String login);
}
