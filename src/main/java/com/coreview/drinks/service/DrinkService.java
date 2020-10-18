package com.coreview.drinks.service;

import com.coreview.drinks.data.drink.Drink;
import com.coreview.drinks.repository.DrinkRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DrinkService {

    final DrinkRepository drinkRepository;

    public DrinkService(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }


    public List<Drink> getAllDrinks() {
       return drinkRepository.findAll();
    }

    public Optional<Drink> getDrinkById(Integer id) {
        return drinkRepository.findById(id);
    }


}
