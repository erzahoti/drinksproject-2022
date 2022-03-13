package com.drinksproject.drinks.controller;

import com.drinksproject.drinks.model.drink.Drink;
import com.drinksproject.drinks.service.DrinkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DrinkController {

    private final DrinkService drinkService;

    public DrinkController(DrinkService drinkService) {
        this.drinkService = drinkService;
    }

    /**
     * Get all drinks
     * @return drinks
     */
    @GetMapping("/drinks")
    public List<Drink> getAllDrinks() {
        return drinkService.getAllDrinks();
    }

}
