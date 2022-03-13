package com.drinkproject.drinks.repository;

import com.drinkproject.drinks.data.bucket.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DiscountRepository extends JpaRepository<Discount, UUID> {

    Optional<Discount> getDiscountByCode(String code);
}