package com.softwarearchitecture.QuickBook.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import com.softwarearchitecture.QuickBook.Dto.HotelDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel_services")

public class HotelService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String serviceName;
    private BigDecimal price;


    public HotelService(long id, String serviceName, BigDecimal price) {
        this.id =id;
        this.serviceName = serviceName;
        this.price = price;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;


    public HotelDto getHotelById(long h_id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHotelById'");
    }
}