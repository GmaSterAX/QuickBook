package com.softwarearchitecture.QuickBook.Controller;

import com.softwarearchitecture.QuickBook.Dto.HotelDto;
import com.softwarearchitecture.QuickBook.Dto.RoomDto;
import com.softwarearchitecture.QuickBook.Service.HotelService;
import com.softwarearchitecture.QuickBook.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RoomController {
    private RoomService roomService;
    private HotelService hotelService;

    @Autowired
    public RoomController(RoomService roomService, HotelService hotelService){
        this.roomService = roomService;
        this.hotelService = hotelService;
    }

    @GetMapping("room-get_{capacity}")
    public ResponseEntity<List<RoomDto>> getRoomByCapacity(@PathVariable("capacity") int capacity){
        List<RoomDto> rooms = roomService.getRoomByCapacity(capacity);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("search/{city}/{hotel_id}/rooms-{capacity}")
    public ResponseEntity<List<RoomDto>> getRoomsByCityAndHotel_IdAndCapacity(@PathVariable("city") String city, @PathVariable("hotel_id") long hotel_id, @PathVariable("capacity") int capacity){
        List<RoomDto> rooms = roomService.getRoomsByCityHotelAndCapacity(city, hotel_id, capacity);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/search")
    public String getRoomsByCityAndCapacity(@RequestParam String city, @RequestParam int capacity, Model model) {
        List<RoomDto> rooms = roomService.getRoomsByCityAndCapacity(city, capacity);
        List<HotelDto> hotels = new ArrayList<>();
        for(RoomDto room : rooms){
            hotels.add(hotelService.getHotelById(room.getH_id()));
        }
        model.addAttribute("hotels", hotels);
        return "reservation";
    }

    @GetMapping("/hotel/{id}")
    public String getHotelDetails(@PathVariable("id") long id, Model model){
        HotelDto hotel = hotelService.getHotelById(id);
        model.addAttribute("hotel",hotel);
        return "hotel-details";
    }
}
