package com.quickbook21.qb21.models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //For PK not to increment by one. Just for unique PKs.
    private long id;
    private String name;
    private String mail;
    private String phone;
    private String password;


}
