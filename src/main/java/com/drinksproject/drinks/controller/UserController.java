package com.drinksproject.drinks.controller;

import com.drinksproject.drinks.model.user.User;
import com.drinksproject.drinks.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@RestController
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get User Information
     * @param id Integer
     * @return User
     */
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") UUID id) {
        var user = this.userService.getUserById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found!");
        }
        return user.get();
    }
}
