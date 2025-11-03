package com.ainzson.usermanagementservice.service;

import com.ainzson.usermanagementservice.dto.RoleDto;
import com.ainzson.usermanagementservice.entities.Role;
import com.ainzson.usermanagementservice.enums.RoleType;

import java.util.UUID;

public interface IRole {

    void createRole(RoleDto roleDto);

    Role getRoleByName(RoleType roleName);

    Role getDefaultRole();

    void assignRolesToUser(UUID user, RoleType roleType);

    void removeRoleFromUser(UUID userId, RoleType roleType);
}
