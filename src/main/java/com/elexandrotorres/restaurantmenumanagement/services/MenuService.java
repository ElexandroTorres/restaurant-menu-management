package com.elexandrotorres.restaurantmenumanagement.services;

import com.elexandrotorres.restaurantmenumanagement.dtos.MenuDto;

import java.util.List;

public interface MenuService {
    MenuDto save(MenuDto menuDto);
    MenuDto update(Long id, MenuDto menuDto);
    void delete(Long id);
    MenuDto getById(Long id);
    List<MenuDto> getAll();
}
