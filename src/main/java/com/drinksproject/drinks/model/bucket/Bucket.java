package com.drinksproject.drinks.model.bucket;

import com.drinksproject.drinks.model.user.User;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bucket")
public class Bucket {

    @Id
    private UUID id;

    @OneToMany
    private List<BucketOrder> orders;

    @Column
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @Column
    private double finalTotalPrice;

    @Column
    private PaymentType paymentType;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private BucketStatus status;

    public Bucket() {
    }

    public List<BucketOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<BucketOrder> orders) {
        this.orders = orders;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public double getFinalTotalPrice() {
        return finalTotalPrice;
    }

    public void setFinalTotalPrice(double finalTotalPrice) {
        this.finalTotalPrice = finalTotalPrice;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BucketStatus getStatus() {
        return status;
    }

    public void setStatus(BucketStatus status) {
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
