package com.softwarearchitecture.QuickBook.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelDto {
    private long id;
    private String name;
    private String address;
    private String city;
    private String phone;
    private double point;
    private String img_url;
}
