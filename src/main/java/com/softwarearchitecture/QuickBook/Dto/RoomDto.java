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

public class RoomDto {

    private int id;
    private int hotel_id;
    private BigDecimal price;
    private boolean reserved;
    private int capacity;

}
