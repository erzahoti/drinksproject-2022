package com.drinksproject.drinks.repository;

import com.drinksproject.drinks.model.bucket.Bucket;
import com.drinksproject.drinks.model.bucket.BucketStatus;
import com.drinksproject.drinks.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BucketRepository extends JpaRepository<Bucket, UUID> {

    Optional<Bucket> getBucketByUserAndStatus(User user, BucketStatus bucketStatus);
}
