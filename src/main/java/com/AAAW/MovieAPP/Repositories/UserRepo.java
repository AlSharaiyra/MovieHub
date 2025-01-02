package com.AAAW.MovieAPP.Repositories;

import com.AAAW.MovieAPP.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
