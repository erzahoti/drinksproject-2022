package com.coreview.drinks.data.bucket;

import javax.persistence.*;

@Entity
@Table(name = "discount",  uniqueConstraints = { @UniqueConstraint(columnNames = "code")})
public class Discount {

    @Id
    @GeneratedValue
    Integer id;

    @Column
    String code;

    @Column
    int percentage;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
