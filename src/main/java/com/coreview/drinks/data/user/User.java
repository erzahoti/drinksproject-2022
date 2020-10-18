package com.coreview.drinks.data.user;

import javax.persistence.*;

@Entity
@Table(name = "res_user")
public class User {

    @Id
    @GeneratedValue
    Integer id;

    @Column
    String email;

    @Column
    String name;

    @Column
    String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
