package com.softwarearchitecture.QuickBook.Controller;

import com.softwarearchitecture.QuickBook.Dto.HotelDto;
import com.softwarearchitecture.QuickBook.Repository.HotelRepository;
import com.softwarearchitecture.QuickBook.Service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HotelController {
    private HotelService hotelService;
    private HotelRepository hotelRepository;

    @Autowired
    public HotelController(HotelService hotelService){
        this.hotelService = hotelService;
    }

    @GetMapping("/hotel-get_{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable("id") long hotelId){
        HotelDto hotelDto = hotelService.getHotelById(hotelId);
        return ResponseEntity.ok(hotelDto);
    }

    @GetMapping("hotel-get_all")
    public ResponseEntity<List<HotelDto>> getAllHotels(){
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @GetMapping("hotel/city/{city}")
    public ResponseEntity<List<HotelDto>> getHotelByCity(@PathVariable("city") String city){
        List<HotelDto> hotels = hotelService.getHotelByCity(city);
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("hotel/point/{point}")
    public ResponseEntity<List<HotelDto>> getHotelByPoint(@PathVariable("point") double point){
        List<HotelDto> hotels = hotelService.getHotelByPoint(point);
        return ResponseEntity.ok(hotels);
    }
}
