package com.drinksproject.drinks.service;

import com.drinksproject.drinks.model.user.User;
import com.drinksproject.drinks.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(UUID id) {
        return this.userRepository.findById(id);
    }
}
