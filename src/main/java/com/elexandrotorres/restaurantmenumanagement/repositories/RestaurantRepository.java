package com.elexandrotorres.restaurantmenumanagement.repositories;

import com.elexandrotorres.restaurantmenumanagement.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
