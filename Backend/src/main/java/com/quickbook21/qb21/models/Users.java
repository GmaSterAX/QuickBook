package com.quickbook21.qb21.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
public class Users {
    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //For PK not to increment by one. Just for unique PKs.
    private int id;
    private String mail;
    private String name;
    private String password;
    private String phone;

    //AllArgsConstructor
    public Users(String name, String mail, String password, String phone) {
        this.mail = mail;
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    //NoArgsConstructor
    public Users(){}

}
