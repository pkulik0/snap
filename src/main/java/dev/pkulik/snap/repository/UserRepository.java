package dev.pkulik.snap.repository;

import dev.pkulik.snap.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
}
