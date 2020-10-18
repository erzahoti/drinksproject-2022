package com.coreview.drinks.repository;

import com.coreview.drinks.data.drink.DrinkType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkTypeRepository extends JpaRepository<DrinkType, Integer> {

}
