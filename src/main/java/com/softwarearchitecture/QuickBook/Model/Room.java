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
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name= "hotel_id", nullable = false)
    private int hotel_id;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "reserved", nullable = false)
    private boolean reserved;

    @Column(name = "capacity", nullable = false)
    private int capacity;



}
