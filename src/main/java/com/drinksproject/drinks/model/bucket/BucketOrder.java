package com.drinksproject.drinks.model.bucket;

import com.drinksproject.drinks.model.drink.Drink;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "bucket_order")
public class BucketOrder {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "drink_id")
    private Drink drink;

    @Column
    private int quantity;

    @Column
    private Date orderDate;

    @Column
    private double price;

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
