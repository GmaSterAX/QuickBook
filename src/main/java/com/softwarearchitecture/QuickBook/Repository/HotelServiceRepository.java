package com.softwarearchitecture.QuickBook.Repository;

import com.softwarearchitecture.QuickBook.Model.HotelService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelServiceRepository extends JpaRepository<HotelService, Long> {

    Optional<HotelService> findById(long hotelServiceId);
    List<HotelService> findByServiceName(String serviceName);
    List<HotelService> findByHotel_Id(long hotelId);}
