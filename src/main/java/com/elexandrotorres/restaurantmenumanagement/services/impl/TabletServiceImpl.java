package com.elexandrotorres.restaurantmenumanagement.services.impl;

import com.elexandrotorres.restaurantmenumanagement.dtos.RestaurantDto;
import com.elexandrotorres.restaurantmenumanagement.dtos.TabletDto;
import com.elexandrotorres.restaurantmenumanagement.exceptions.ResourceNotFoundException;
import com.elexandrotorres.restaurantmenumanagement.models.Restaurant;
import com.elexandrotorres.restaurantmenumanagement.models.Tablet;
import com.elexandrotorres.restaurantmenumanagement.repositories.RestaurantRepository;
import com.elexandrotorres.restaurantmenumanagement.repositories.TabletRepository;
import com.elexandrotorres.restaurantmenumanagement.services.TabletService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TabletServiceImpl implements TabletService {

    private final TabletRepository tabletRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public TabletServiceImpl(TabletRepository tabletRepository, RestaurantRepository restaurantRepository) {
        this.tabletRepository = tabletRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    @Transactional
    public TabletDto save(TabletDto tabletDto) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(tabletDto.restaurantDto().id());

        if (restaurantOptional.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + tabletDto.restaurantDto().id());
        }

        Tablet tablet = new Tablet();
        tablet.setBrand(tabletDto.brand());
        tablet.setModel(tabletDto.model());
        tablet.setCode(tablet.getCode());
        tablet.setTableCode(tabletDto.tableCode());
        tablet.setRestaurant(restaurantOptional.get());

        Tablet savedTablet = tabletRepository.save(tablet);
        return toDto(savedTablet);
    }

    @Override
    @Transactional
    public TabletDto update(Long id, TabletDto tabletDto) {
        Optional<Tablet> tabletOptional = tabletRepository.findById(id);

        if (tabletOptional.isEmpty()) {
            throw new ResourceNotFoundException("Tablet not found with id: " + id);
        }

        Restaurant restaurant = restaurantRepository.findById(tabletDto.restaurantDto().id())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        Tablet tablet = tabletOptional.get();

        tablet.setModel(tabletDto.model());
        tablet.setBrand(tabletDto.brand());
        tablet.setCode(tabletDto.code());
        tablet.setTableCode(tabletDto.tableCode());
        tablet.setRestaurant(restaurant);

        Tablet updatedTablet = tabletRepository.save(tablet);
        return toDto(updatedTablet);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        tabletRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tablet not found"));
        tabletRepository.deleteById(id);
    }

    @Override
    public TabletDto getById(Long id) {
        Tablet tablet = tabletRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tablet not found"));
        return toDto(tablet);
    }

    @Override
    public List<TabletDto> getAll() {
        return tabletRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private TabletDto toDto(Tablet tablet) {
        RestaurantDto restaurantDto = new RestaurantDto(tablet.getRestaurant().getId(), tablet.getRestaurant().getName(), tablet.getRestaurant().getAddress(), tablet.getRestaurant().getPhoneNumber());

        return new TabletDto(
                tablet.getId(),
                tablet.getCode(),
                tablet.getBrand(),
                tablet.getModel(),
                restaurantDto,
                tablet.getTableCode()
        );
    }
}