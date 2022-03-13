package com.drinkproject.drinks.repository;

import com.drinkproject.drinks.data.bucket.BucketOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BucketOrderRepository extends JpaRepository<BucketOrder, UUID> {

}
