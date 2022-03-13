package com.drinkproject.drinks.repository;

import com.drinkproject.drinks.data.bucket.Bucket;
import com.drinkproject.drinks.data.bucket.BucketStatus;
import com.drinkproject.drinks.data.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BucketRepository extends JpaRepository<Bucket, UUID> {

    Optional<Bucket> getBucketByUserAndStatus(User user, BucketStatus bucketStatus);
}