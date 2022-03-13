package com.drinksproject.drinks.service;

import com.drinksproject.drinks.controller.contracts.DrinkRequest;
import com.drinksproject.drinks.data.bucket.*;
import com.drinksproject.drinks.data.drink.Drink;
import com.drinksproject.drinks.data.user.User;
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
            bucket = new Bucket();
            bucket.setUser(user);
            bucket.setFinalTotalPrice(0);
            bucket.setTotalPrice(0);
            bucket.setStatus(BucketStatus.DRAFT);
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
        if (bucketToUpdate.isEmpty()) throw new EntityNotFoundException("Bucket not found!");
        bucketOrderService.updateBucketOrderQuantity(bucketOrder);
        updateTotal(bucketToUpdate.get());
        return this.bucketRepository.save(bucketToUpdate.get());
    }

    public Bucket applyDiscount(UUID bucketId, String discountCode) {
        Optional<Bucket> bucketToUpdate = this.bucketRepository.findById(bucketId);
        if (bucketToUpdate.isEmpty()) throw new NullPointerException();
        Optional<Discount> discount = this.discountService.getDiscountByCode(discountCode);
        if (discount.isEmpty()) throw new NullPointerException();
        bucketToUpdate.get().setDiscount(discount.get());
        bucketToUpdate.get().setFinalTotalPrice(this.calculateDiscount(bucketToUpdate.get().getTotalPrice(), discount.get().getPercentage()));
        this.bucketRepository.save(bucketToUpdate.get());
        return bucketToUpdate.get();
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
        if (discount != null) total = calculateDiscount(total, discount.getPercentage());
        bucketToUpdate.setFinalTotalPrice(total);
    }

    double calculatePrice(List<BucketOrder> bucketOrderList) {
        return bucketOrderList.stream().mapToDouble(order -> order.getPrice() * order.getQuantity()).sum();
    }

    double calculateDiscount(double existingPrice, int percentage) {
        return existingPrice - (((float) percentage / 100) * existingPrice);
    }
}
