package com.coreview.drinks.service;

import com.coreview.drinks.data.user.User;
import com.coreview.drinks.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Integer id) {
        return this.userRepository.findById(id);
    }
}
