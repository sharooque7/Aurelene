package com.ainzson.usermanagementservice.service;

import com.ainzson.usermanagementservice.dto.RoleDto;
import com.ainzson.usermanagementservice.entities.Role;
import com.ainzson.usermanagementservice.entities.User;
import com.ainzson.usermanagementservice.enums.RoleType;
import com.ainzson.usermanagementservice.exception.ResourceNotFoundException;
import com.ainzson.usermanagementservice.mapper.RoleMapper;
import com.ainzson.usermanagementservice.repository.RoleRepository;
import com.ainzson.usermanagementservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoleService implements IRole {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public void  createRole(RoleDto roleDto) {
        Role role = RoleMapper.toEntity(roleDto);
        roleRepository.save(role);
    }

    @Override
    public Role getRoleByName(RoleType roleName) {
        return roleRepository.findByRole(roleName).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    }

    @Override
    public Role getDefaultRole() {
        return roleRepository.findByRole(RoleType.ROLE_USER).orElseThrow(() -> new ResourceNotFoundException("Default role not found"));
    }

    @Override
    public void assignRolesToUser(UUID userId, RoleType roleType) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );

        Role role = roleRepository.findByRole(roleType)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public void removeRoleFromUser(UUID userId, RoleType roleType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Role role = roleRepository.findByRole(roleType)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        user.getRoles().remove(role);
        userRepository.save(user);
    }
}
