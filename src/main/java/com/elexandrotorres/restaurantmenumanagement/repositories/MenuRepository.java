package com.elexandrotorres.restaurantmenumanagement.repositories;

import com.elexandrotorres.restaurantmenumanagement.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
