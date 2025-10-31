package com.ainzson.usermanagementservice.repository;

import com.ainzson.usermanagementservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    Optional<User> findById(UUID id);

    void deleteById(UUID id);

    Optional<User> findByEmailAndDeletedFalse(String email);
}
