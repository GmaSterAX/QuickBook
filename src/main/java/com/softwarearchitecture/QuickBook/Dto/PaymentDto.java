package com.softwarearchitecture.QuickBook.Dto;


import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentDto {
    private long id;
    private BigDecimal reservation_price;
    private String payment_method;
    private boolean payment_situation;
    private long reservation_id;


}

