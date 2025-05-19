package com.softwarearchitecture.QuickBook.Controller;

import com.softwarearchitecture.QuickBook.Dto.RoomDto;
import com.softwarearchitecture.QuickBook.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService){
        this.roomService = roomService;
    }

    //birden fazla otel yazacağımız için list alıyoruz
    @GetMapping("room-get_{capacity}")
    public ResponseEntity<List<RoomDto>> getRoomByCapacity(@PathVariable("capacity") int capacity) {
        List<RoomDto> roomDto = roomService.getByRoomCapacity(capacity);
        return ResponseEntity.ok(roomDto);
    }

}
