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

    public HotelService(long id, String serviceName) {
        this.id = id;
        this.serviceName = serviceName;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

}