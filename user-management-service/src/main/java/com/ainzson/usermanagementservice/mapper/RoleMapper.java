package com.ainzson.usermanagementservice.mapper;

import com.ainzson.usermanagementservice.dto.RoleDto;
import com.ainzson.usermanagementservice.entities.Role;
import com.ainzson.usermanagementservice.enums.RoleType;

public class RoleMapper {

    public static Role toEntity(RoleDto roleDto) {
        Role role = new Role();
        RoleType roleType = RoleType.valueOf(roleDto.getRole());
        role.setRole(roleType);
        return role;
    }
}
