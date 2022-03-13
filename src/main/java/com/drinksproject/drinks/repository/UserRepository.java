package com.drinksproject.drinks.repository;

import com.drinksproject.drinks.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Override
    Optional<User> findById(UUID id);
}
