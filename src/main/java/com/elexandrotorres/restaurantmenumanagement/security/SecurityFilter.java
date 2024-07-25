package com.elexandrotorres.restaurantmenumanagement.security;

import com.elexandrotorres.restaurantmenumanagement.models.User;
import com.elexandrotorres.restaurantmenumanagement.repositories.UserRepository;
import com.elexandrotorres.restaurantmenumanagement.services.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final AuthService authService;
    private final UserRepository userRepository;

    @Autowired
    public SecurityFilter(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractTokenHeader(request);

        if(token != null) {
            String email = authService.validateTokenJwt(token);
            User user = userRepository.findByEmail(email);

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    public String extractTokenHeader(HttpServletRequest httpServletRequest) {
        var authHeader = httpServletRequest.getHeader("Authorization");

        if(authHeader == null) {
            return null;
        }

        if(!authHeader.startsWith("Bearer ")) {
            return null;
        }

        //pode ser desse outro jeito
//        if(!authHeader.split(" ")[0].equals("Bearer")) {
//            return null;
//        }

        return authHeader.substring(7); //autheHeader.split(" ")[1]

    }
}
