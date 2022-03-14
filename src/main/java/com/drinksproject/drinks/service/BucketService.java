package com.drinksproject.drinks.service;

import com.drinksproject.drinks.controller.contracts.DrinkRequest;
import com.drinksproject.drinks.model.bucket.*;
import com.drinksproject.drinks.model.drink.Drink;
import com.drinksproject.drinks.model.user.User;
import com.drinksproject.drinks.repository.BucketRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BucketService {

    final BucketOrderService bucketOrderService;
    final DrinkService drinkService;
    final BucketRepository bucketRepository;
    final DiscountService discountService;

    public BucketService(BucketOrderService bucketOrderService, DrinkService drinkService, BucketRepository bucketRepository, DiscountService discountService) {
        this.bucketOrderService = bucketOrderService;
        this.drinkService = drinkService;
        this.bucketRepository = bucketRepository;
        this.discountService = discountService;
    }

    public Optional<Bucket> getBucket(User user) {
        return bucketRepository.getBucketByUserAndStatus(user, BucketStatus.DRAFT);
    }

    public Optional<Bucket> getBucket(UUID bucketId) {
        return bucketRepository.findById(bucketId);
    }


    public Bucket addToBucket(DrinkRequest drinkRequest, User user) {
        var drink = drinkService.getDrinkById(drinkRequest.getDrinkId());
        if(drink.isEmpty()) {
            throw new EntityNotFoundException("Drink not found");
        }
        Optional<Bucket> checkBucket = getBucket(user);
        Bucket bucket;
        if (checkBucket.isEmpty()) {
            bucket = createBucket(user);
        } else {
            bucket = checkBucket.get();
        }
        List<BucketOrder> bucketOrderList = bucket.getOrders() != null ? bucket.getOrders() : new ArrayList<>();
        bucketOrderList = updateBucketOrderList(bucketOrderList, drink.get(), drinkRequest.getQuantity());
        bucket.setOrders(bucketOrderList);
        updateTotal(bucket);
        this.bucketRepository.save(bucket);
        return bucket;
    }

    public Bucket updateBucket(BucketOrder bucketOrder, Bucket bucket) {
        Optional<Bucket> bucketToUpdate = bucketRepository.findById(bucket.getId());
        if (bucketToUpdate.isEmpty()) {
            throw new EntityNotFoundException("Bucket not found!");
        }
        bucketOrderService.updateBucketOrderQuantity(bucketOrder);
        updateTotal(bucketToUpdate.get());
        return this.bucketRepository.save(bucketToUpdate.get());
    }

    public Bucket applyDiscount(UUID bucketId, String discountCode) {
        Optional<Bucket> bucketToUpdate = this.bucketRepository.findById(bucketId);
        if (bucketToUpdate.isEmpty()) {
            throw new NullPointerException();
        }
        var bucket = bucketToUpdate.get();
        Optional<Discount> discount = this.discountService.getDiscountByCode(discountCode);
        if (discount.isEmpty()) {
            throw new NullPointerException();
        }
        bucket.setDiscount(discount.get());
        bucket.setFinalTotalPrice(this.calculateDiscount(bucket.getTotalPrice(), discount.get().getPercentage()));
        this.bucketRepository.save(bucket);
        return bucket;
    }

    public Bucket pay(UUID bucketId, PaymentType paymentType) {
        var bucketToUpdate = bucketRepository.findById(bucketId);
        if(bucketToUpdate.isEmpty()) {
            throw new EntityNotFoundException("Bucket not found!");
        }
        var bucket = bucketToUpdate.get();
        bucket.setPaymentType(paymentType);
        bucket.setStatus(BucketStatus.COMPLETED);
        bucketRepository.save(bucket);
        return bucket;
    }

    List<BucketOrder> updateBucketOrderList(List<BucketOrder> bucketOrderList, Drink drink, int quantity) {
        Optional<BucketOrder> bucketOrderExist = bucketOrderList.stream().filter(bucketOrder -> bucketOrder.getDrink().equals(drink)).findFirst();
        BucketOrder bucketOrder;
        if (bucketOrderExist.isEmpty()) {
            bucketOrder = this.bucketOrderService.createBucketOrder(drink);
            bucketOrderList.add(bucketOrder);
        } else {
            bucketOrder = bucketOrderExist.get();
            bucketOrder.setQuantity(bucketOrder.getQuantity() + quantity);
        }
        return bucketOrderList;
    }

    private void updateTotal(Bucket bucketToUpdate) {
        double total = calculatePrice(bucketToUpdate.getOrders());
        bucketToUpdate.setTotalPrice(total);
        Discount discount = bucketToUpdate.getDiscount();
        var finalTotal = discount != null ? calculateDiscount(total, discount.getPercentage()) : total;
        bucketToUpdate.setFinalTotalPrice(finalTotal);
    }

    private double calculatePrice(List<BucketOrder> bucketOrderList) {
        return bucketOrderList.stream().mapToDouble(order -> order.getPrice() * order.getQuantity()).sum();
    }

    private double calculateDiscount(double existingPrice, int percentage) {
        return existingPrice - (((float) percentage / 100) * existingPrice);
    }

    private Bucket createBucket(User user) {
        var bucket = new Bucket();
        bucket.setUser(user);
        bucket.setFinalTotalPrice(0);
        bucket.setTotalPrice(0);
        bucket.setStatus(BucketStatus.DRAFT);
        return bucket;
    }
}
