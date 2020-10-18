package com.coreview.drinks.repository;

import com.coreview.drinks.data.bucket.Bucket;
import com.coreview.drinks.data.bucket.BucketStatus;
import com.coreview.drinks.data.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BucketRepository extends JpaRepository<Bucket, Integer> {

    Optional<Bucket> getBucketByUserAndStatus(User user, BucketStatus bucketStatus);
}
