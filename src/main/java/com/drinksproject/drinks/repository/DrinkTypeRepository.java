package com.drinksproject.drinks.repository;

import com.drinksproject.drinks.model.drink.DrinkType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DrinkTypeRepository extends JpaRepository<DrinkType, UUID> {

}
