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
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private BigDecimal reservation_price;
    private String payment_method;
    private boolean payment_situation;

    @OneToOne
    @JoinColumn(name = "reservation_id", unique = true)
    private Reservation reservation;

    public Payment(long id, BigDecimal reservation_price, String payment_method, boolean payment_situation) {
        this.id = id;
        this.reservation_price = reservation_price;
        this.payment_method = payment_method;
        this.payment_situation = payment_situation;
    }
}
