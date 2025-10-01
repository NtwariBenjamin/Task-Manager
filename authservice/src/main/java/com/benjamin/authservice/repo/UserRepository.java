package com.benjamin.authservice.repo;

import com.benjamin.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String username);

    Optional<User> findByFirstName(String firstName);

    Optional<User> findByLastName(String lastName);
}
