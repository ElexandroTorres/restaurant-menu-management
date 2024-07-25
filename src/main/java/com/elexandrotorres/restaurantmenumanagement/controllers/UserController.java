package com.elexandrotorres.restaurantmenumanagement.controllers;

import com.elexandrotorres.restaurantmenumanagement.dtos.UserDto;
import com.elexandrotorres.restaurantmenumanagement.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    private ResponseEntity<?> save(@Valid @RequestBody UserDto userDto) {
        try {
            UserDto savedUser = userService.save(userDto);
            return ResponseEntity.ok(savedUser);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
