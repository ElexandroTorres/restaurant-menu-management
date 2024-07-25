package com.elexandrotorres.restaurantmenumanagement.models;

import jakarta.persistence.*;

@Entity
@Table(name = "TB_ITEM")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
