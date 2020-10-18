package com.coreview.drinks.repository;

import com.coreview.drinks.data.bucket.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {

    Optional<Discount> getDiscountByCode(String code);
}
