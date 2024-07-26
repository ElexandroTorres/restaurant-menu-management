package com.elexandrotorres.restaurantmenumanagement.repositories;

import com.elexandrotorres.restaurantmenumanagement.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
