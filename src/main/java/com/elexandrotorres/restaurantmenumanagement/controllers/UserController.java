package com.elexandrotorres.restaurantmenumanagement.controllers;

import com.elexandrotorres.restaurantmenumanagement.dtos.UserDto;
import com.elexandrotorres.restaurantmenumanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserDto save(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

    @GetMapping("/admin")
    private String getAdmin() {
        return "Permissão de administrador";
    }

    @GetMapping("/manager")
    private String getManager() {
        return "Permissão de Gerente";
    }
}
