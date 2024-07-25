package com.elexandrotorres.restaurantmenumanagement.services.impl;

import com.elexandrotorres.restaurantmenumanagement.dtos.MenuDto;
import com.elexandrotorres.restaurantmenumanagement.dtos.RestaurantDto;
import com.elexandrotorres.restaurantmenumanagement.exceptions.ResourceNotFoundException;
import com.elexandrotorres.restaurantmenumanagement.models.Menu;
import com.elexandrotorres.restaurantmenumanagement.models.Restaurant;
import com.elexandrotorres.restaurantmenumanagement.repositories.MenuRepository;
import com.elexandrotorres.restaurantmenumanagement.repositories.RestaurantRepository;
import com.elexandrotorres.restaurantmenumanagement.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public MenuServiceImpl(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public MenuDto save(MenuDto menuDto) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(menuDto.restaurantDto().id());

        if (restaurantOptional.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + menuDto.restaurantDto().id());
        }

        Menu menu = new Menu();
        menu.setTitle(menuDto.title());
        menu.setRestaurant(restaurantOptional.get());

        Menu savedMenu = menuRepository.save(menu);
        return toDto(savedMenu);
    }

    @Override
    public MenuDto update(Long id, MenuDto menuDto) {
        Optional<Menu> menuOptional = menuRepository.findById(id);

        if (menuOptional.isEmpty()) {
            throw new ResourceNotFoundException("Menu not found with id: " + id);
        }

        Restaurant restaurant = restaurantRepository.findById(menuDto.restaurantDto().id())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + menuDto.restaurantDto().id()));

        Menu menu = menuOptional.get();

        menu.setTitle(menuDto.title());
        menu.setRestaurant(restaurant);

        Menu updatedMenu = menuRepository.save(menu);
        return toDto(updatedMenu);
    }

    @Override
    public void delete(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id: " + id));
        menuRepository.deleteById(id);
    }

    @Override
    public MenuDto getById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id: " + id));
        return toDto(menu);
    }

    @Override
    public List<MenuDto> getAll() {
        return menuRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private MenuDto toDto(Menu menu) {
        return new MenuDto(
                menu.getId(),
                menu.getTitle(),
                new RestaurantDto(
                        menu.getRestaurant().getId(),
                        menu.getRestaurant().getName(),
                        menu.getRestaurant().getAddress(),
                        menu.getRestaurant().getPhoneNumber()
                ),
                Collections.emptySet() //Remover
        );
    }
}
