package com.elexandrotorres.restaurantmenumanagement.dtos;

import com.elexandrotorres.restaurantmenumanagement.enums.RoleEnum;

public record UserDto (
        String name,
        String email,
        String password,
        RoleEnum role
) {
}
