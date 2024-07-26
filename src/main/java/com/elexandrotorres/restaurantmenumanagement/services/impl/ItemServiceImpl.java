package com.elexandrotorres.restaurantmenumanagement.services.impl;

import com.elexandrotorres.restaurantmenumanagement.dtos.CategoryDto;
import com.elexandrotorres.restaurantmenumanagement.dtos.ItemDto;
import com.elexandrotorres.restaurantmenumanagement.dtos.MenuDto;
import com.elexandrotorres.restaurantmenumanagement.dtos.RestaurantDto;
import com.elexandrotorres.restaurantmenumanagement.exceptions.ResourceNotFoundException;
import com.elexandrotorres.restaurantmenumanagement.models.Category;
import com.elexandrotorres.restaurantmenumanagement.models.Item;
import com.elexandrotorres.restaurantmenumanagement.repositories.CategoryRepository;
import com.elexandrotorres.restaurantmenumanagement.repositories.ItemRepository;
import com.elexandrotorres.restaurantmenumanagement.services.ItemService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public ItemDto save(ItemDto itemDto) {
        Optional<Category> categoryOptional = categoryRepository.findById(itemDto.categoryDto().id());

        if (categoryOptional.isEmpty()) {
            throw new ResourceNotFoundException("Category not found with id: " + itemDto.categoryDto().id());
        }

        Category category = categoryOptional.get();

        Item item = new Item();
        item.setName(itemDto.name());
        item.setPrice(itemDto.price());
        item.setCategory(category);

        Item savedItem = itemRepository.save(item);
        return toDto(savedItem);
    }

    @Override
    @Transactional
    public ItemDto update(Long id, ItemDto itemDto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        Category category = categoryRepository.findById(itemDto.categoryDto().id())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        item.setName(itemDto.name());
        item.setPrice(itemDto.price());
        item.setCategory(category);

        Item updatedItem = itemRepository.save(item);
        return toDto(updatedItem);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));
        itemRepository.deleteById(id);
    }

    @Override
    public ItemDto getById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));
        return toDto(item);
    }

    @Override
    public List<ItemDto> getAll() {
        return itemRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ItemDto toDto(Item item) {
        RestaurantDto restaurantDto = new RestaurantDto(
                item.getMenu().getRestaurant().getId(),
                item.getMenu().getRestaurant().getName(),
                item.getMenu().getRestaurant().getAddress(),
                item.getMenu().getRestaurant().getPhoneNumber());
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getPrice(),
                new MenuDto(item.getMenu().getId(), item.getMenu().getTitle(), restaurantDto, Collections.emptySet()),
                new CategoryDto(item.getCategory().getId())
        );
    }
}