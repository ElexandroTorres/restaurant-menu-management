package com.elexandrotorres.restaurantmenumanagement.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.elexandrotorres.restaurantmenumanagement.dtos.AuthDto;
import com.elexandrotorres.restaurantmenumanagement.models.User;
import com.elexandrotorres.restaurantmenumanagement.repositories.UserRepository;
import com.elexandrotorres.restaurantmenumanagement.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    @Override
    public String getToken(AuthDto authDto) {
        User userInDataBase = userRepository.findByEmail(authDto.email());

        return generateTokenJwt(userInDataBase);
    }

    public String generateTokenJwt(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("temporary-secret");

            return JWT.create()
                    .withIssuer("restaurantmenumanagement")
                    .withSubject(user.getEmail())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
        } catch (Exception exception) {
            throw new RuntimeException("Error creating JWT", exception);
        }
    }

    public String validateTokenJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("temporary-secret");

            return JWT.require(algorithm)
                    .withIssuer("restaurantmenumanagement")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception exception) {
            return "";
        }
    }

    private Instant getExpirationDate() {
        return LocalDateTime
                .now()
                .plusHours(12)
                .toInstant(ZoneOffset.of("-03"));
    }
}
