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
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "reservation_id", nullable = false)
    private int reservation_id;

    @Column(name = "reservation_price", nullable = false)
    private BigDecimal reservation_price;

    @Column(name = "payment_method", nullable = false)
    private String payment_method;

    @Column(name = "payment_situation", nullable = false)
    private boolean payment_situation;
}
