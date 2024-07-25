package com.elexandrotorres.restaurantmenumanagement.dtos;

import java.util.Set;

public record MenuDto (
        Long id,
        String title,
        RestaurantDto restaurantDto,
        Set<ItemDto> items
) {
}
