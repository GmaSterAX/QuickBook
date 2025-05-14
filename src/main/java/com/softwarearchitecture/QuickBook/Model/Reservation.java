package com.softwarearchitecture.QuickBook.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", nullable = false)
    private int user_id;

    @Column(name = "hotel_id", nullable = false)
    private int hotel_id;

    @Column(name = "room_id", nullable = false)
    private int room_id;

    @Column(name = "check_in", nullable = false)
    private boolean check_in;

    @Column(name = "check_out", nullable = false)
    private boolean check_out;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
}

