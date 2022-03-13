package com.drinkproject.drinks;

import com.drinkproject.drinks.contracts.BucketUpdateRequest;
import com.drinkproject.drinks.contracts.DrinkRequest;
import com.drinkproject.drinks.data.bucket.Bucket;
import com.drinkproject.drinks.data.bucket.BucketOrder;
import com.drinkproject.drinks.data.bucket.BucketStatus;
import com.drinkproject.drinks.data.drink.Drink;
import com.drinkproject.drinks.data.user.User;
import com.drinkproject.drinks.repository.BucketOrderRepository;
import com.drinkproject.drinks.repository.BucketRepository;
import com.drinkproject.drinks.repository.DrinkRepository;
import com.drinkproject.drinks.service.UserService;
import com.drinkproject.drinks.utils.MockData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BucketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    BucketRepository bucketRepository;

    @MockBean
    BucketOrderRepository bucketOrderRepository;

    @MockBean
    DrinkRepository drinkRepository;

    ObjectMapper objectMapper;

    Drink drink;
    BucketOrder bucketOrder;
    User user;
    Bucket bucket;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        drink = MockData.getDrink(UUID.randomUUID());
        bucketOrder = MockData.getBucketOrder(UUID.randomUUID(), drink);
        user = MockData.getUser(UUID.randomUUID());
        bucket = MockData.getBucket(UUID.randomUUID(), bucketOrder, user, null);

        Mockito.when(userService.getUserById(any())).thenReturn(java.util.Optional.of(user));
        Mockito.when(bucketRepository.save(any())).thenReturn(bucket);
        Mockito.when(bucketRepository.getBucketByUserAndStatus(user, BucketStatus.DRAFT)).thenReturn(Optional.ofNullable(bucket));
        Mockito.when(bucketRepository.findById(any())).thenReturn(Optional.of(bucket));
        Mockito.when(bucketOrderRepository.findById(any())).thenReturn(Optional.of(bucketOrder));
        Mockito.when(drinkRepository.findById(any())).thenReturn(Optional.of(drink));

    }

    @Test
    public void getBucket() throws Exception {
        var result = mockMvc.perform(get("/bucket/read")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        var returnedBucket = objectMapper.readValue(result.getResponse().getContentAsString(), Bucket.class);
        assertEquals(bucket.getId(), returnedBucket.getId());
    }

    @Test
    public void addDrink() throws Exception {
        var drinkRequest = new DrinkRequest(UUID.randomUUID(), 100);
        String requestJson = objectMapper.writeValueAsString(drinkRequest);
        var result = mockMvc.perform(post("/bucket/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        var returnedBucket = objectMapper.readValue(result.getResponse().getContentAsString(), Bucket.class);
        assertNotNull(returnedBucket);
        assertEquals(returnedBucket.getOrders().size(), 1);
        assertNotNull(returnedBucket.getOrders().stream().filter(bucketOrder -> bucketOrder.getDrink().getId().equals(drinkRequest.getDrinkId())));
    }

    @Test
    public void updateBucket() throws Exception {
        bucketOrder.setQuantity(12);
        var bucketUpdateRequest = new BucketUpdateRequest(bucketOrder);
        String requestJson = objectMapper.writeValueAsString(bucketUpdateRequest);
        var result = mockMvc.perform(put("/bucket/" + bucket.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        var updatedBucket = objectMapper.readValue(result.getResponse().getContentAsString(), Bucket.class);
        assertEquals(bucket.getOrders().get(0).getQuantity(), updatedBucket.getOrders().get(0).getQuantity());
    }

    @Test
    public void applyDiscount() throws Exception {
        var price = bucket.getFinalTotalPrice();
        var path = "/bucket/" + bucket.getId() + "/apply_discount/" + MockData.DISCOUNT_CODE;
        var result = mockMvc.perform(put(path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        var returnedBucket = objectMapper.readValue(result.getResponse().getContentAsString(), Bucket.class);
        assertEquals(returnedBucket.getFinalTotalPrice(), price/2);
    }
}
