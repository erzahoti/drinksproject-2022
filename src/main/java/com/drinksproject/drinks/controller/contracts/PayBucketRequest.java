package com.drinksproject.drinks.controller.contracts;

import com.drinksproject.drinks.data.bucket.PaymentType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class PayBucketRequest {

    private final UUID bucketId;
    private final PaymentType paymentType;

    @JsonCreator
    public PayBucketRequest(@JsonProperty("bucketId") UUID bucketId, @JsonProperty("paymentType") PaymentType paymentType) {
        this.bucketId = bucketId;
        this.paymentType = paymentType;
    }

    public UUID getBucketId() {
        return bucketId;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }
}
