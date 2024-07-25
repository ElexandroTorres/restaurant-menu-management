package com.elexandrotorres.restaurantmenumanagement.controllers;

import com.elexandrotorres.restaurantmenumanagement.dtos.AuthDto;
import com.elexandrotorres.restaurantmenumanagement.dtos.AuthResponseDto;
import com.elexandrotorres.restaurantmenumanagement.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
    public ResponseEntity<?> authenticate(@RequestBody AuthDto authDto) {
        try {
            var userAuthToken = new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());
            authenticationManager.authenticate(userAuthToken);

            String token = authService.getToken(authDto);
            return ResponseEntity.ok(new AuthResponseDto("Login successful", token));
        } catch (BadCredentialsException badCredentialsException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponseDto("Invalid email or password", null));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthResponseDto("An error occurred: " + exception.getMessage(), null));
        }
    }
}
