package com.ainzson.usermanagementservice.repository;

import com.ainzson.usermanagementservice.entities.Role;
import com.ainzson.usermanagementservice.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByRole(RoleType roleName);
}
