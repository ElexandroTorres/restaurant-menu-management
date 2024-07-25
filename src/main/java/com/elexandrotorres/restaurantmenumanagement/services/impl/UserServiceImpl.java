package com.elexandrotorres.restaurantmenumanagement.services.impl;

import com.elexandrotorres.restaurantmenumanagement.dtos.UserDto;
import com.elexandrotorres.restaurantmenumanagement.models.User;
import com.elexandrotorres.restaurantmenumanagement.repositories.UserRepository;
import com.elexandrotorres.restaurantmenumanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto save(UserDto userDto) {
        User userExists = userRepository.findByEmail(userDto.email());

        if(userExists != null) {
            throw new RuntimeException("User already exists");
        }

        String encryptedPassword = passwordEncoder.encode(userDto.password());

        User user = new User(userDto.name(), userDto.email(), encryptedPassword, userDto.role());

        User savedUser = userRepository.save(user);

        return new UserDto(savedUser.getName(), savedUser.getEmail(), savedUser.getPassword(), savedUser.getRole());
    }
}
