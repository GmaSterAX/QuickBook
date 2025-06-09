package com.softwarearchitecture.QuickBook.Controller;

import com.softwarearchitecture.QuickBook.Dto.RoomServiceDto;
import com.softwarearchitecture.QuickBook.Service.RoomServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoomServiceController {
    private RoomServiceService roomServiceService;

    @Autowired
    public RoomServiceController(RoomServiceService roomServiceService){
        this.roomServiceService = roomServiceService;
    }

    @GetMapping("rooms/services_{id}")
    public ResponseEntity<RoomServiceDto> getRoomServiceById(@PathVariable("id") long id){
        RoomServiceDto roomService = roomServiceService.getRoomServiceById(id);
        return ResponseEntity.ok(roomService);
    }

    @GetMapping("room_{id}/service")
    public ResponseEntity<List<RoomServiceDto>> getRoomServiceByRoomId(@PathVariable("id") long id) {
        List<RoomServiceDto> roomServices = roomServiceService.getRoomServiceByRoomId(id);
        return new ResponseEntity<>(roomServices, HttpStatus.OK);
    }

    @GetMapping("/room_{roomId}/services")
    public ResponseEntity<List<RoomServiceDto>> getRoomServiceByRoomIdAndRoomType(@PathVariable("roomId") long roomId){
        List<RoomServiceDto> roomServices = roomServiceService.getRoomServiceByRoomIdAndRoomType(roomId,"Delux" );
        return ResponseEntity.ok(roomServices);
    }
}
