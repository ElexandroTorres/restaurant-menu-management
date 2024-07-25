package com.elexandrotorres.restaurantmenumanagement.dtos;

import jakarta.validation.constraints.NotBlank;

public record RestaurantDto(
        Long id,
        @NotBlank(message = "Name of the restaurant is required")
        String name,
        @NotBlank(message = "Address of the restaurant is required")
        String address,
        @NotBlank(message = "Phone number of the restaurant is required")
        String phoneNumber
) {
}
