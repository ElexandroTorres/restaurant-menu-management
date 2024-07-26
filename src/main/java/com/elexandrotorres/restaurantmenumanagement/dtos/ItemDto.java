package com.elexandrotorres.restaurantmenumanagement.dtos;

import com.elexandrotorres.restaurantmenumanagement.models.Menu;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public record ItemDto(
        Long id,
        String name,
        double price,
        MenuDto menuDto,
        CategoryDto categoryDto
) {
}