package com.drinkproject.drinks.controller;

import com.drinkproject.drinks.contracts.BucketUpdateRequest;
import com.drinkproject.drinks.contracts.DrinkRequest;
import com.drinkproject.drinks.contracts.PayBucketRequest;
import com.drinkproject.drinks.data.bucket.Bucket;

import com.drinkproject.drinks.service.BucketOrderService;
import com.drinkproject.drinks.service.BucketService;
import com.drinkproject.drinks.service.DrinkService;
import com.drinkproject.drinks.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@RestController
public class BucketController {

    final BucketService bucketService;
    final UserService userService;
    final DrinkService drinkService;
    final BucketOrderService bucketOrderService;

    public BucketController(BucketService bucketService, UserService userService, DrinkService drinkService, BucketOrderService bucketOrderService) {
        this.bucketService = bucketService;
        this.userService = userService;
        this.drinkService = drinkService;
        this.bucketOrderService = bucketOrderService;
    }

    /**
     * Add a drink to the bucket
     *
     * @param drinkRequest DrinkRequest
     * @return Bucket
     */
    @PostMapping("/bucket/add")
    public Bucket addToBucket(@RequestBody DrinkRequest drinkRequest) {
        var user = userService.getUserById(UUID.randomUUID());
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        return bucketService.addToBucket(drinkRequest, user.get());
    }

    /**
     * Get Bucket
     *
     * @return User's Bucket
     */
    @GetMapping("bucket/read")
    public Bucket getBucket() throws Exception {

        // if user is logged in, we have to get logged in user id.
        var user = userService.getUserById(UUID.randomUUID());
        if (user.isEmpty()) {
            throw new Exception("User does not exists!");
        }
        var bucket = bucketService.getBucket(user.get());
        if (bucket.isEmpty()) {
            throw new Exception("User does not have a bucket!");
        }
        return bucket.get();

    }

    /**
     * Update Bucket
     *
     * @param request Bucket and Bucket order to update.
     * @return Updated Bucket
     */
    @PutMapping("bucket/{bucketId}")
    public Bucket updateBucket(@PathVariable("bucketId") UUID bucketId, @RequestBody BucketUpdateRequest request) {
        var bucket = bucketService.getBucket(bucketId);
        if (bucket.isEmpty()) {
            throw new EntityNotFoundException("Bucket not found");
        }
        return bucketService.updateBucket(request.getBucketOrder(), bucket.get());
    }

    /**
     * Apply discount
     *
     * @param bucketId int
     * @param code     String
     * @return Bucket with new total
     */
    @PutMapping("bucket/{bucketId}/apply_discount/{code}")
    public Bucket applyDiscount(@PathVariable("bucketId") UUID bucketId, @PathVariable("code") String code) {
        return bucketService.applyDiscount(bucketId, code);
    }

    /**
     * Pay and finish bucket
     *
     * @param payBucketRequest PayBucketRequest
     * @return Paid Bucket
     */
    @PutMapping("bucket/{bucketId}/pay")
    public Bucket pay(@RequestBody PayBucketRequest payBucketRequest) {
        return bucketService.pay(payBucketRequest.getBucketId(), payBucketRequest.getPaymentType());
    }
}
