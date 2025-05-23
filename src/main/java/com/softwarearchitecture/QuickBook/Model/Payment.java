package com.softwarearchitecture.QuickBook.Model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;


@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long reservation_id;
    private BigDecimal reservation_price;
    private String payment_method;
    private boolean payment_situation;
}
