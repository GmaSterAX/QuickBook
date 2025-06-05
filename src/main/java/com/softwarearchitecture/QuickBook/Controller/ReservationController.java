package com.softwarearchitecture.QuickBook.Controller;
import com.softwarearchitecture.QuickBook.Dto.HotelDto;
import com.softwarearchitecture.QuickBook.Dto.ReservationDto;
import com.softwarearchitecture.QuickBook.Dto.UserDto;
import com.softwarearchitecture.QuickBook.Service.ReservationService;
import com.softwarearchitecture.QuickBook.Service.UserService;
import com.softwarearchitecture.QuickBook.Service.HotelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@Controller
public class ReservationController {

    private ReservationService reservationService;
    private UserService userService;
    private HotelService hotelService;

    @Autowired
    public ReservationController(ReservationService reservationService,
                                 UserService userService,
                                 HotelService hotelService){
        this.reservationService = reservationService;
        this.userService = userService;
        this.hotelService = hotelService;
    }

    @GetMapping("reservation_{id}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable("id") long reservationId){
        ReservationDto reservationDto = reservationService.getReservationById(reservationId);
        return ResponseEntity.ok(reservationDto);
    }

    @GetMapping("/my-reservations")
    public String getUserReservations(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userMail = authentication.getName();
        UserDto user = userService.getUserByMail(userMail);
        List<ReservationDto> reservations = reservationService.getReservationByUserId(user.getId());
        model.addAttribute("reservations", reservations);
        
        List<HotelDto> hotels = new ArrayList<>();
        for (ReservationDto reservation : reservations) {
            hotels.add(hotelService.getHotelById(reservation.getH_id()));
            System.out.println(reservation.getStart_date());
            System.out.println(reservation.getEnd_date());
        }
        model.addAttribute("hotels", hotels);
        return "my-reservations";
    }

    @PostMapping("/reservation/create")
    public ResponseEntity<String> createReservation(@RequestBody ReservationDto reservationDto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userMail = authentication.getName();

            UserDto user = userService.getUserByMail(userMail);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Kullanıcı bulunamadı.");
            }

            reservationDto.setU_id(user.getId());
            reservationService.saveReservation(reservationDto);
            System.out.println(reservationDto);
            return ResponseEntity.ok("Başarılı");

        } catch (Exception e) {
            // 🧨 Tüm hataları terminale yaz
            System.err.println("Rezervasyon oluşturulurken hata oluştu: " + e.getMessage());
            e.printStackTrace(); // stacktrace detaylı yazmak için
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Sunucu hatası: " + e.getMessage());
        }
    }
}