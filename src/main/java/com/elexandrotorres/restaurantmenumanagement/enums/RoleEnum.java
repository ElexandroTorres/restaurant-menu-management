package com.elexandrotorres.restaurantmenumanagement.enums;

public enum RoleEnum {
    ADMIN("admin"),
    MANAGER("manager");

    private String role;

    RoleEnum(String role) {
        this.role = role;
    }
}
