package com.softwarearchitecture.QuickBook.Repository;

import com.softwarearchitecture.QuickBook.Model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findAllByCityIgnoreCase(String city);
    List<Hotel> findAllByPointBetween(double min, double max);
    Hotel findByName(String hotelName);
    //Hotel findById(long hotelId); remzininki
    Optional<Hotel> findById(Long hotelId);;

}

