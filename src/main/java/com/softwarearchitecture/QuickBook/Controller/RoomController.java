package com.softwarearchitecture.QuickBook.Controller;

import com.softwarearchitecture.QuickBook.Dto.RoomDto;
import com.softwarearchitecture.QuickBook.Mapper.RoomMapper;
import com.softwarearchitecture.QuickBook.Model.Room;
import com.softwarearchitecture.QuickBook.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoomController {
    private RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService){
        this.roomService = roomService;
    }

    @GetMapping("room-get_{capacity}")
    public ResponseEntity<List<RoomDto>> getRoomByCapacity(@PathVariable("capacity") int capacity){
        List<RoomDto> rooms = roomService.getRoomByCapacity(capacity);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("search/{city}/rooms-{capacity}")
    public ResponseEntity<List<RoomDto>> getRoomsByCityAndCapacity(@PathVariable("city") String city, @PathVariable("capacity") int capacity) {
        List<RoomDto> rooms = roomService.getRoomsByCityAndCapacity(city, capacity);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("search/{city}/{hotel_id}/rooms-{capacity}")
    public ResponseEntity<List<RoomDto>> getRoomsByCityAndHotel_IdAndCapacity(@PathVariable("city") String city, @PathVariable("hotel_id") long hotel_id, @PathVariable("capacity") int capacity){
        List<RoomDto> rooms = roomService.getRoomsByCityHotelAndCapacity(city, hotel_id, capacity);
        return ResponseEntity.ok(rooms);
    }
}
