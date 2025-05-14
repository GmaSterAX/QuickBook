package com.softwarearchitecture.QuickBook.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", nullable = false)
    private int user_id;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "state", nullable = false)
    private boolean state;
}

