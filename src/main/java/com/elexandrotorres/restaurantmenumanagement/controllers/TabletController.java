package com.elexandrotorres.restaurantmenumanagement.controllers;

import com.elexandrotorres.restaurantmenumanagement.dtos.RestaurantDto;
import com.elexandrotorres.restaurantmenumanagement.dtos.TabletDto;
import com.elexandrotorres.restaurantmenumanagement.services.TabletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tablets")
@Validated
public class TabletController {

    private final TabletService tabletService;

    @Autowired
    public TabletController(TabletService tabletService) {
        this.tabletService = tabletService;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody TabletDto tabletDto) {
        try {
            TabletDto savedTablet = tabletService.save(tabletDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTablet);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody TabletDto tabletDto) {
        try {
            TabletDto updatedTablet = tabletService.update(id, tabletDto);
            return ResponseEntity.ok(updatedTablet);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            tabletService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            TabletDto tabletDto = tabletService.getById(id);
            return ResponseEntity.ok(tabletDto);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<TabletDto> tablets = tabletService.getAll();
            return ResponseEntity.ok(tablets);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
