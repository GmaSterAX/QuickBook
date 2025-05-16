package com.softwarearchitecture.QuickBook.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CampaignDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "hotel_id", nullable = false)
    private int hotel_id;

    @Column(name = "discount_code", nullable = false)
    private String discount_code;

    @Column(name = "discount_amount", nullable = false)
    private BigDecimal discount_amount;

    @Column(name = "start_date", nullable = false)
    private LocalDate start_date;

    @Column(name = "finish_date", nullable = false)
    private LocalDate finish_date;
}

