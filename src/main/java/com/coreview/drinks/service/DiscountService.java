package com.coreview.drinks.service;

import com.coreview.drinks.data.bucket.Discount;
import com.coreview.drinks.repository.DiscountRepository;
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
