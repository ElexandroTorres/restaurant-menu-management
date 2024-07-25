package com.elexandrotorres.restaurantmenumanagement.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "TB_MENU")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Item> items;
}
