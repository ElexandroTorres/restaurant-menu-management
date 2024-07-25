package com.elexandrotorres.restaurantmenumanagement.services.impl;

import com.elexandrotorres.restaurantmenumanagement.dtos.RestaurantDto;
import com.elexandrotorres.restaurantmenumanagement.exceptions.ResourceNotFoundException;
import com.elexandrotorres.restaurantmenumanagement.models.Restaurant;
import com.elexandrotorres.restaurantmenumanagement.repositories.RestaurantRepository;
import com.elexandrotorres.restaurantmenumanagement.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public RestaurantDto save(RestaurantDto restaurantDto) {
        Restaurant restaurant = new Restaurant(
                restaurantDto.name(),
                restaurantDto.address(),
                restaurantDto.phoneNumber()
        );
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return toDto(savedRestaurant);
    }

    @Override
    public RestaurantDto update(Long id, RestaurantDto restaurantDto) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);

        if (restaurantOptional.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + id);
        }

        Restaurant restaurant = restaurantOptional.get();
        restaurant.setName(restaurantDto.name());
        restaurant.setAddress(restaurantDto.address());
        restaurant.setPhoneNumber(restaurantDto.phoneNumber());
        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return toDto(updatedRestaurant);
    }

    @Override
    public void delete(Long id) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);

        if (restaurantOptional.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + id);
        }

        restaurantRepository.deleteById(id);
    }

    @Override
    public RestaurantDto getById(Long id) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);

        if (restaurantOptional.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + id);
        }

        Restaurant restaurant = restaurantOptional.get();
        return toDto(restaurant);
    }

    @Override
    public List<RestaurantDto> getAll() {
        return restaurantRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private RestaurantDto toDto(Restaurant restaurant) {
        return new RestaurantDto(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getPhoneNumber()
        );
    }
}
