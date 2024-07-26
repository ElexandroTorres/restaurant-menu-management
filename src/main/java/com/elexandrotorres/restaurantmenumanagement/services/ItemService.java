package com.elexandrotorres.restaurantmenumanagement.services;

import com.elexandrotorres.restaurantmenumanagement.dtos.ItemDto;
import java.util.List;

public interface ItemService {
    ItemDto save(ItemDto itemDto);
    ItemDto update(Long id, ItemDto itemDto);
    void delete(Long id);
    ItemDto getById(Long id);
    List<ItemDto> getAll();
}
