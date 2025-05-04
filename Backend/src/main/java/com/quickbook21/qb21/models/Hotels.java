package com.quickbook21.qb21.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
public class Hotels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    private String city;
    private String phone;
    private double point;


    public Hotels(String name, String address, String city, String phone, double point) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.point = point;
    }

    public Hotels(){}
}
