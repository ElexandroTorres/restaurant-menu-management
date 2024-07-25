package com.elexandrotorres.restaurantmenumanagement.services;

import com.elexandrotorres.restaurantmenumanagement.dtos.TabletDto;

import java.util.List;

public interface TabletService {
    TabletDto save(TabletDto tabletDto);
    TabletDto update(Long id, TabletDto tabletDto);
    void delete(Long id);
    TabletDto getById(Long id);
    List<TabletDto> getAll();
}
