package com.coreview.drinks;

import com.coreview.drinks.controller.BucketController;
import com.coreview.drinks.controller.DrinkController;
import com.coreview.drinks.data.bucket.*;
import com.coreview.drinks.data.drink.Drink;
import com.coreview.drinks.data.drink.DrinkType;
import com.coreview.drinks.data.user.User;
import com.coreview.drinks.repository.BucketRepository;
import com.coreview.drinks.repository.DrinkRepository;
import com.coreview.drinks.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DrinksApplicationTests {

    @InjectMocks
    DrinkController drinkController;

    @InjectMocks
    BucketService bucketService;

    @Mock
    DiscountService discountService;

    @Mock
    DrinkService drinkService;

    @Mock
    UserService userService;

    @Mock
    BucketRepository bucketRepository;

    @Mock
    BucketOrderService bucketOrderService;

    @Test
    public void getAllDrinks() {
        DrinkType drinkType = new DrinkType();
        drinkType.setDescription("ITALIAN COFFEE");
        drinkType.setId(1);
        drinkType.setName("ITALIAN_COFFEE");

        Drink d = new Drink();
        d.setDescription("coffee");
        d.setDrinkType(drinkType);
        d.setQuantity(100);

        List<Drink>  list = new ArrayList<>();
        list.add(d);
        Mockito.when(drinkService.getAllDrinks()).thenReturn(list);
        List<Drink> drinks = drinkController.getAllDrinks().getBody();

        assert drinks != null;
        assertTrue(drinks.size() > 0);
    }

    @Test
    public void addToBucket() {
        Map<String, Object> data = getMockData();
        User user = (User) data.get("user");
        Drink drink = (Drink) data.get("drink");
        BucketOrder bucketOrder = (BucketOrder) data.get("bucketOrder");

        Mockito.when(userService.getUserById(1)).thenReturn(java.util.Optional.of(user));
        Mockito.when(drinkService.getDrinkById(1)).thenReturn(java.util.Optional.of(drink));
        Mockito.when(bucketRepository.getBucketByUserAndStatus(user, BucketStatus.DRAFT)).thenReturn(Optional.empty());
        Mockito.when( bucketOrderService.createBucketOrder(drink)).thenReturn(bucketOrder);

        Bucket bucket = bucketService.addToBucket(drink, user);
        boolean anyMatch = bucket.getOrders().stream().anyMatch(order -> order.getDrink().getId().equals(drink.getId()));
        assertTrue(anyMatch);
    }

    @Test
    public void applyDiscount() {
        Map<String, Object> data = getMockData();
        Bucket bucket = (Bucket) data.get("bucket");
        Discount discount = (Discount) data.get("discount");

        double finalTotalWithDiscount = bucket.getTotalPrice() - (((float) discount.getPercentage()/100)*bucket.getTotalPrice());
        Mockito.when(discountService.getDiscountByCode(discount.getCode())).thenReturn(java.util.Optional.of(discount));
        Mockito.when(bucketRepository.findById(bucket.getId())).thenReturn(java.util.Optional.of(bucket));

        bucket = bucketService.applyDiscount(bucket, discount.getCode());
        assertEquals(finalTotalWithDiscount, bucket.getFinalTotalPrice());
    }

    @Test
    public void pay(){
        Map<String, Object> data = getMockData();
        Bucket bucket = (Bucket) data.get("bucket");
        Mockito.when(bucketRepository.getOne(bucket.getId())).thenReturn(bucket);
        bucket = bucketService.pay(bucket, PaymentType.CARD.name());
        assertTrue(bucket.getStatus().equals(BucketStatus.COMPLETED) && bucket.getPaymentType().equals(PaymentType.CARD));

    }


    private Map<String, Object> getMockData() {

        DrinkType drinkType = new DrinkType();
        drinkType.setDescription("ITALIAN COFFEE");
        drinkType.setId(1);
        drinkType.setName("ITALIAN_COFFEE");

        Drink drink = new Drink();
        drink.setId(1);
        drink.setDescription("coffee");
        drink.setDrinkType(drinkType);
        drink.setQuantity(100);
        drink.setPrice(5);

        User user = new User();
        user.setId(1);
        user.setAddress("Messina, Italy");
        user.setEmail("e@gmail.com");
        user.setName("Test");

        Discount discount = new Discount();
        discount.setId(1);
        discount.setCode("50BLACKFRIDAY");
        discount.setPercentage(50);

        BucketOrder bucketOrder = new BucketOrder();
        bucketOrder.setId(1);
        bucketOrder.setQuantity(2);
        bucketOrder.setPrice(drink.getPrice());
        bucketOrder.setOrderDate(new Date());
        bucketOrder.setDrink(drink);
        List<BucketOrder> bucketOrders = new ArrayList<>();
        bucketOrders.add(bucketOrder);

        Bucket bucket = new Bucket();
        bucket.setOrders(bucketOrders);
        bucket.setUser(user);
        bucket.setId(1);
        bucket.setStatus(BucketStatus.DRAFT);
        bucket.setTotalPrice(bucketOrder.getPrice()*bucketOrder.getQuantity());
        bucket.setFinalTotalPrice(bucketOrder.getPrice()*bucketOrder.getQuantity());
        bucket.setDiscount(discount);

        Map<String, Object> res = new HashMap<>();
        res.put("user", user);
        res.put("bucketOrder", bucketOrder);
        res.put("drink", drink);
        res.put("bucket", bucket);
        res.put("discount", discount);

        return res;
    }

}
