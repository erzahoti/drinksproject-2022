package com.coreview.drinks.data.bucket;

import com.coreview.drinks.data.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bucket")
public class Bucket {

    @Id
    @GeneratedValue
    Integer id;

    @OneToMany
    List<BucketOrder> orders;

    @Column
    double totalPrice;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    Discount discount;

    @Column
    double finalTotalPrice;

    @Column
    PaymentType paymentType;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column
    BucketStatus status;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
