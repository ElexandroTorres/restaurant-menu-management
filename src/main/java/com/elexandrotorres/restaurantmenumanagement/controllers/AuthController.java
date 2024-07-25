package com.elexandrotorres.restaurantmenumanagement.controllers;

import com.elexandrotorres.restaurantmenumanagement.dtos.AuthDto;
import com.elexandrotorres.restaurantmenumanagement.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String authenticate(@RequestBody AuthDto authDto) {
        var userAuthToken = new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());
        authenticationManager.authenticate(userAuthToken);

        return authService.getToken(authDto);
    }
}
