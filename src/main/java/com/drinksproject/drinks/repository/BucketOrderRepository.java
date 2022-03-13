package com.drinksproject.drinks.repository;

import com.drinksproject.drinks.model.bucket.BucketOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BucketOrderRepository extends JpaRepository<BucketOrder, UUID> {

}
