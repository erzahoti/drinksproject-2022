package com.coreview.drinks.data.bucket;

import com.coreview.drinks.data.drink.Drink;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bucket_order")
public class BucketOrder {

    @Id
    @GeneratedValue
    Integer id;

    @ManyToOne
    @JoinColumn(name = "drink_id")
    Drink drink;

    @Column
    int quantity;

    @Column
    Date orderDate;

    @Column
    double price;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
