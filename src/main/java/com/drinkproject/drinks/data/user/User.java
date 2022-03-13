package com.drinkproject.drinks.data.user;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "res_user")
public class User {

    @Id
    private UUID id;

    @Column
    private String email;

    @Column
    private String name;

    private @Column
    String address;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
