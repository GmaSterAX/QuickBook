package com.softwarearchitecture.QuickBook.Controller;

import com.softwarearchitecture.QuickBook.Dto.HotelDto;
import com.softwarearchitecture.QuickBook.Service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class HotelController {
    private HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService){
        this.hotelService = hotelService;
    }

    @GetMapping("/hotel/{id}")
    public String getHotelPage(@PathVariable("id") long hotelId, Model model) {
        HotelDto hotel = hotelService.getHotelById(hotelId);
        model.addAttribute("hotel", hotel);
        return "hotel-details";
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

