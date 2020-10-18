package com.coreview.drinks.data.drink;

import javax.persistence.*;

@Entity
@Table(name = "drink")
public class Drink {

    @Id
    @GeneratedValue
    Integer id;

    @OneToOne
    @JoinColumn(name = "drink_type_id")
    DrinkType drinkType;

    @Column
    String description;

    @Column
    int quantity;

    @Column
    double price;

    public DrinkType getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(DrinkType drinkType) {
        this.drinkType = drinkType;
    }

    public String  getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice(){
        return this.price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
