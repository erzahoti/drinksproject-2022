package com.coreview.drinks.repository;

import com.coreview.drinks.data.bucket.BucketOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BucketOrderRepository extends JpaRepository<BucketOrder, Integer> {

}
