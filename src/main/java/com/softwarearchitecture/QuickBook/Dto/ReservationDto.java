package com.softwarearchitecture.QuickBook.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    private long id;
    private LocalDate start_date;
    private LocalDate end_date;
    private BigDecimal price;
    private long u_id;
    private long h_id;
    private long r_id;
}
