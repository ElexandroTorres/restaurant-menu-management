package com.elexandrotorres.restaurantmenumanagement.dtos;

import com.elexandrotorres.restaurantmenumanagement.enums.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDto (
        @NotBlank(message = "The Name of the user should be informed")
        String name,
        @Email(message = "Email of the user should be valid")
        @NotBlank(message = "Email of the user should be informed")
        String email,
        @NotBlank(message = "Password should be informed")
        String password,
        @NotNull(message = "Role of the user should be informed")
        RoleEnum role
) {
}
