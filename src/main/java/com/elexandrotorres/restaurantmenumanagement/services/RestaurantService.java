package com.elexandrotorres.restaurantmenumanagement.services;

import com.elexandrotorres.restaurantmenumanagement.dtos.RestaurantDto;

import java.util.List;

public interface RestaurantService {
    RestaurantDto save(RestaurantDto restaurantDto);

    RestaurantDto update(Long id, RestaurantDto restaurantDto);

    void delete(Long id);

    RestaurantDto getById(Long id);

    List<RestaurantDto> getAll();
}
