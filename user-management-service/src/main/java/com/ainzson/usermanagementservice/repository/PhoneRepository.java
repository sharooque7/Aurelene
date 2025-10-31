package com.ainzson.usermanagementservice.repository;

import com.ainzson.usermanagementservice.entities.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneNumber, UUID> {
    Optional<Set<PhoneNumber>> findByUserId(UUID userId);
}
