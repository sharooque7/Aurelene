package com.ainzson.usermanagementservice.repository;

import com.ainzson.usermanagementservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    @Query("""
        SELECT u FROM User u
        LEFT JOIN FETCH u.roles
        WHERE u.email = :email AND u.deleted = false
    """)
    Optional<User> findByEmailAndDeletedFalseWithRoles(@Param("email") String email);


    Optional<User> findById(UUID id);

    void deleteById(UUID id);

    Optional<User> findByEmailAndDeletedFalse(String email);
}
