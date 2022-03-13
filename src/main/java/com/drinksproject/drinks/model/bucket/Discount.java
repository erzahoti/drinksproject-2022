package com.drinksproject.drinks.model.bucket;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "discount",  uniqueConstraints = { @UniqueConstraint(columnNames = "code")})
public class Discount {

    @Id
    private UUID id;

    @Column
    private String code;

    @Column
    private int percentage;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
