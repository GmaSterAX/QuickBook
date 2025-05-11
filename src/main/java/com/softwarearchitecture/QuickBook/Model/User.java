package com.softwarearchitecture.QuickBook.Model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{
    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String mail;
    private String phone;
    private String password;

}
