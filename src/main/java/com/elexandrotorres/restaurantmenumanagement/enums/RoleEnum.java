package com.elexandrotorres.restaurantmenumanagement.enums;

public enum RoleEnum {
    ADMIN("admin"),
    MANAGER("manager"),
    MENU_MANAGER("menu-manager"),
    ITEM_MANAGER("item-manager"),
    TABLET("tablet");

    private String role;

    RoleEnum(String role) {
        this.role = role;
    }
}
