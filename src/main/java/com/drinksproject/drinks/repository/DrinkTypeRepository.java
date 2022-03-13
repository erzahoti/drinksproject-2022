package com.drinksproject.drinks.repository;

import com.drinksproject.drinks.data.drink.DrinkType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DrinkTypeRepository extends JpaRepository<DrinkType, UUID> {

}
