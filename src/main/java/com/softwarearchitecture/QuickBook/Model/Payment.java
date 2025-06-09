package com.softwarearchitecture.QuickBook.Model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String payment_situation;
    private BigDecimal reservation_price;
    @OneToOne
    @JoinColumn(name = "reservation_id", unique = true)
    private Reservation reservation;
}
