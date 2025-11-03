package com.ainzson.usermanagementservice.enums;

public enum RoleType {
    ROLE_USER,
    ROLE_MANAGER,
    ROLE_ADMIN;

    public static RoleType fromString(String role) {
        try {
            return RoleType.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid role type: " + role);
        }
    }
}
