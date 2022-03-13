package com.drinksproject.drinks.controller.contracts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class DrinkRequest {
    private final UUID drinkId;
    private final int quantity;

    @JsonCreator
    public DrinkRequest(@JsonProperty("drinkId") UUID drinkId, @JsonProperty("quantity") int quantity) {
        this.drinkId = drinkId;
        this.quantity = quantity;
    }

    public UUID getDrinkId() {
        return drinkId;
    }

    public int getQuantity() {
        return quantity;
    }
}