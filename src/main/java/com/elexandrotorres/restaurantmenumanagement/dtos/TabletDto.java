package com.elexandrotorres.restaurantmenumanagement.dtos;

public record TabletDto(
        Long id,
        String code,
        String brand,
        String model,
        RestaurantDto restaurantDto,
        Integer tableCode
) {
}
