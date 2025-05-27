package com.softwarearchitecture.QuickBook.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    private long id;
    private boolean check_in;
    private boolean check_out;
    private BigDecimal price;
    private long u_id;
    private long h_id;
    private long r_id;
}
