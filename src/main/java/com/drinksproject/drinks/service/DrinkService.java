package com.drinksproject.drinks.service;

import com.drinksproject.drinks.data.drink.Drink;
import com.drinksproject.drinks.repository.DrinkRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DrinkService {

    final DrinkRepository drinkRepository;

    public DrinkService(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }


    public List<Drink> getAllDrinks() {
       return drinkRepository.findAll();
    }

    public Optional<Drink> getDrinkById(UUID id) {
        return drinkRepository.findById(id);
    }


}
