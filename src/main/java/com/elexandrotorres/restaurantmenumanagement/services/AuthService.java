package com.elexandrotorres.restaurantmenumanagement.services;

import com.elexandrotorres.restaurantmenumanagement.dtos.AuthDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    public String getToken(AuthDto authDto);
    public String validateTokenJwt(String token);
}
