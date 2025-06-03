package com.softwarearchitecture.QuickBook.Controller;

import com.softwarearchitecture.QuickBook.Dto.ActivityDto;
import com.softwarearchitecture.QuickBook.Dto.CommentDto;
import com.softwarearchitecture.QuickBook.Dto.HotelDto;
import com.softwarearchitecture.QuickBook.Dto.HotelServiceDto;
import com.softwarearchitecture.QuickBook.Dto.RoomDto;
import com.softwarearchitecture.QuickBook.Dto.RoomServiceDto;
import com.softwarearchitecture.QuickBook.Service.ActivityService;
import com.softwarearchitecture.QuickBook.Service.CommentService;
import com.softwarearchitecture.QuickBook.Service.HotelService;
import com.softwarearchitecture.QuickBook.Service.HotelServiceService;
import com.softwarearchitecture.QuickBook.Service.RoomService;
import com.softwarearchitecture.QuickBook.Service.RoomServiceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class RoomController {
    private RoomService roomService;
    private HotelService hotelService;
    private HotelServiceService hotelServiceService;
    private ActivityService activityService;
    private RoomServiceService roomServiceService;
    private CommentService commentService;

    @Autowired
    public RoomController(RoomService roomService,
                          HotelService hotelService,
                          HotelServiceService hotelServiceService,
                          ActivityService activityService,
                          RoomServiceService roomServiceService,
                          CommentService commentService){
        this.roomService = roomService;
        this.hotelService = hotelService;
        this.hotelServiceService = hotelServiceService;
        this.activityService = activityService;
        this.roomServiceService = roomServiceService;
        this.commentService = commentService;
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

        Set<Long> addedHotelIds = new HashSet<>();
        List<HotelDto> hotels = new ArrayList<>();

        for (RoomDto room : rooms) {
            Long hotelId = room.getH_id();
            if (addedHotelIds.add(hotelId)) { // Set'e eklenirse true döner
                hotels.add(hotelService.getHotelById(hotelId));
            }
        }

        model.addAttribute("hotels", hotels);
        return "reservation";
    }

    @GetMapping("/hotel/{id}")
    public String getHotelDetails(@PathVariable("id") long id, Model model){
        HotelDto hotel = hotelService.getHotelById(id);
        model.addAttribute("hotel",hotel);
        List<HotelServiceDto> hotelServices = hotelServiceService.getAllByHotelId(id);
        model.addAttribute("hotelServices",hotelServices);
        List<ActivityDto> activities = activityService.getAllByHotelId(id);
        model.addAttribute("activities", activities);
        List<RoomDto> rooms = roomService.getRoomByHotel_Id(id);
        long[] room_ids = new long[3];
        for (RoomDto room : rooms) {
            List<RoomServiceDto> r_Services = roomServiceService.getRoomServiceByRoomId(room.getRoom_id());
            for(RoomServiceDto r_Service : r_Services){
                if(r_Service.getRoomType().equals("Basic"))
                    room_ids[0] = r_Service.getR_id();
                else if(r_Service.getRoomType().equals("Lux"))
                    room_ids[1] = r_Service.getR_id();
                else if(r_Service.getRoomType().equals("Delux"))
                    room_ids[2] = r_Service.getR_id();
            }
        }
        List<RoomServiceDto> basicRoomServices = roomServiceService.getRoomServiceByRoomIdAndRoomType(room_ids[0], "Basic");
        model.addAttribute("basicRoomServices", basicRoomServices);
        List<RoomServiceDto> luxRoomServices = roomServiceService.getRoomServiceByRoomIdAndRoomType(room_ids[1], "Lux");
        model.addAttribute("luxRoomServices", luxRoomServices);
        List<RoomServiceDto> deluxRoomServices = roomServiceService.getRoomServiceByRoomIdAndRoomType(room_ids[2], "Delux");
        model.addAttribute("deluxRoomServices", deluxRoomServices);

        RoomDto basicRoom = roomService.getRoomById(room_ids[0]);
        RoomDto luxRoom = roomService.getRoomById(room_ids[1]);
        RoomDto deluxRoom = roomService.getRoomById(room_ids[2]);

        model.addAttribute("basicPrice", basicRoom);
        model.addAttribute("luxPrice", luxRoom);
        model.addAttribute("deluxPrice", deluxRoom);

        try {
            List<CommentDto> comments = commentService.getCommentByHotelId(id);
            for (CommentDto comment : comments) {
                System.out.println(comment.getUser_comment());
            }
            model.addAttribute("comments", comments);
        } catch (Exception e) {
            e.printStackTrace(); // bu terminalde kesin gösterir
        }
        return "hotel-details";
    }

}
