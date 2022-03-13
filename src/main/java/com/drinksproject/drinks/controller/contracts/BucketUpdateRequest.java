package com.drinksproject.drinks.controller.contracts;

import com.drinksproject.drinks.data.bucket.BucketOrder;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BucketUpdateRequest {

    private final BucketOrder bucketOrder;

    @JsonCreator
    public BucketUpdateRequest(@JsonProperty("bucketOrder") BucketOrder bucketOrder) {
        this.bucketOrder = bucketOrder;
    }

    public BucketOrder getBucketOrder() {
        return bucketOrder;
    }

}
