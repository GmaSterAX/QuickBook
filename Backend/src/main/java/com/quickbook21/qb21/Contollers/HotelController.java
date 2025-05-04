package com.quickbook21.qb21.Contollers;

import com.quickbook21.qb21.Repositories.HotelRepository;
import com.quickbook21.qb21.models.Hotels;
import com.quickbook21.qb21.models.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/qb21/")
public class HotelController {
    private final HotelRepository hotelRepository;

    public HotelController(HotelRepository hotelRepository, HotelRepository hotelRepository1) {
        this.hotelRepository = hotelRepository;
    }
    @GetMapping("hotels")
    public ResponseEntity<List<Hotels>> getHotel(){
        List<Hotels> hotels = new ArrayList<>();
        hotels.add(new Hotels("Hotel1","zattrürzor","İstanabul","45678", 8.3));
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("hotels/{id}")
    public Hotels HotelDetail(@PathVariable int id){
        return new Hotels("Hotel1","zattrürzor","İstanabul","45678", 8.3);
    }
}
