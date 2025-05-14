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
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "hotel_id", nullable = false)
    private int hotel_id;

    @Column(name = "service_name", nullable = false)
    private String service_name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
}