package com.elexandrotorres.restaurantmenumanagement.repositories;

import com.elexandrotorres.restaurantmenumanagement.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
