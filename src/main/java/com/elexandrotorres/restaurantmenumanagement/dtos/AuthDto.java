package com.elexandrotorres.restaurantmenumanagement.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthDto(
        @NotBlank(message = "The Name of the user should be informed")
        String email,
        @NotBlank(message = "Password should be informed")
        String password
) {
}
