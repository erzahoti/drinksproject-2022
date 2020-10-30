package com.coreview.drinks.repository;

import com.coreview.drinks.data.bucket.BucketOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketOrderRepository extends JpaRepository<BucketOrder, Integer> {

}
