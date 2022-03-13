package com.drinkproject.drinks.service;

import com.drinkproject.drinks.data.bucket.Discount;
import com.drinkproject.drinks.repository.DiscountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiscountService {

    final DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public Optional<Discount> getDiscountByCode(String code) {
        return discountRepository.getDiscountByCode(code);
    }
}
