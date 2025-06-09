package com.softwarearchitecture.QuickBook.Dto;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private long id;
    private String payment_situation;
    private long reservation_id;
    private BigDecimal reservation_price;
}

