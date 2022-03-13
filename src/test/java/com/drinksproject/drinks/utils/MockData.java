package com.drinksproject.drinks.utils;

import com.drinksproject.drinks.data.bucket.Bucket;
import com.drinksproject.drinks.data.bucket.BucketOrder;
import com.drinksproject.drinks.data.bucket.BucketStatus;
import com.drinksproject.drinks.data.bucket.Discount;
import com.drinksproject.drinks.data.drink.Drink;
import com.drinksproject.drinks.data.drink.DrinkType;
import com.drinksproject.drinks.data.user.User;

import java.util.*;

public class MockData {

    public static final String DISCOUNT_CODE = "50BLACKFRIDAY";

    public static User getUser(UUID userId) {
        User user = new User();
        user.setId(userId);
        user.setAddress("Oslo, Norway");
        user.setEmail("e@gmail.com");
        user.setName("Test");
        return user;
    }

    public static Drink getDrink(UUID drinkId) {
        DrinkType drinkType = new DrinkType();
        drinkType.setDescription("ITALIAN COFFEE");
        drinkType.setId(UUID.randomUUID());
        drinkType.setName("ITALIAN_COFFEE");

        Drink drink = new Drink();
        drink.setId(drinkId);
        drink.setDescription("coffee");
        drink.setDrinkType(drinkType);
        drink.setQuantity(100);
        drink.setPrice(5);

        return drink;
    }

    public static BucketOrder getBucketOrder(UUID bucketOrderId, Drink drink) {
        BucketOrder bucketOrder = new BucketOrder();
        bucketOrder.setId(bucketOrderId);
        bucketOrder.setQuantity(2);
        bucketOrder.setPrice(drink.getPrice());
        bucketOrder.setOrderDate(new Date());
        bucketOrder.setDrink(drink);
        return bucketOrder;
    }

    public static Bucket getBucket(UUID bucketId, BucketOrder bucketOrder, User user, Discount discount) {
        Bucket bucket = new Bucket();
        bucket.setOrders(List.of(bucketOrder));
        bucket.setUser(user);
        bucket.setId(bucketId);
        bucket.setStatus(BucketStatus.DRAFT);
        bucket.setTotalPrice(bucketOrder.getPrice() * bucketOrder.getQuantity());
        bucket.setFinalTotalPrice(bucketOrder.getPrice() * bucketOrder.getQuantity());
        bucket.setDiscount(discount);
        return bucket;
    }
}
