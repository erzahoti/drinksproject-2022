package com.coreview.drinks.controller;

import com.coreview.drinks.data.bucket.Bucket;
import com.coreview.drinks.data.bucket.BucketOrder;
import com.coreview.drinks.data.drink.Drink;
import com.coreview.drinks.data.user.User;
import com.coreview.drinks.service.BucketOrderService;
import com.coreview.drinks.service.BucketService;
import com.coreview.drinks.service.DrinkService;
import com.coreview.drinks.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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

    @PostMapping("/bucket/add")
    public ResponseEntity<Bucket> addToBucket(@RequestBody Drink drinkToAdd) {
        try {
            Drink drink = drinkService.getDrinkById(drinkToAdd.getId()).get();
            User user = userService.getUserById(1).get();
            return new ResponseEntity<>(bucketService.addToBucket(drink, user), new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Drink failed to add to the bucket!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("bucket/read")
    public ResponseEntity<Optional<Bucket>> getBucket() {
        try {
            Optional<User> user = userService.getUserById(1);
            return new ResponseEntity<>(bucketService.getBucket(user.get()), new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Something went wrong!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("bucket/update")
    public ResponseEntity<Bucket> updateBucket(@RequestBody JsonNode request) throws JsonProcessingException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BucketOrder bucketOrder = objectMapper.readValue(request.get("bucketOrder").toString(), BucketOrder.class);
            Bucket bucket = objectMapper.readValue(request.get("bucket").toString(), Bucket.class);
            return new ResponseEntity<>(bucketService.updateBucket(bucketOrder, bucket), new HttpHeaders(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity("Bucket failed to update!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("bucket/apply_discount/{code}")
    public ResponseEntity<Bucket> applyDiscount(@RequestBody Bucket bucket, @PathVariable String code) {
        try {
            bucket = bucketService.applyDiscount(bucket, code);
            return new ResponseEntity<>(bucket, new HttpHeaders(), HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity("Discount not found!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("bucket/pay")
    public ResponseEntity<Bucket> pay(@RequestBody JsonNode request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Bucket bucket = objectMapper.readValue(request.get("bucket").toString(), Bucket.class);
        String paymentType = request.get("paymentType").textValue();

        if (paymentType.equalsIgnoreCase("CASH") && bucket.getFinalTotalPrice() > 10)
            return new ResponseEntity("For more than 10 $ you should pay by Card!", new HttpHeaders(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(bucketService.pay(bucket, paymentType), new HttpHeaders(), HttpStatus.OK);
    }
}
