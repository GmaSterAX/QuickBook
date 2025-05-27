package com.softwarearchitecture.QuickBook.Controller;
import com.softwarearchitecture.QuickBook.Dto.ReservationDto;
import com.softwarearchitecture.QuickBook.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reservations/")
public class ReservationController {

    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @GetMapping("reservation_{id}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable("id") long reservationId){
        ReservationDto reservationDto = reservationService.getReservationById(reservationId);
        return ResponseEntity.ok(reservationDto);
    }

    @GetMapping("user_{userId}/reservations")
    public ResponseEntity<List<ReservationDto>> getReservationsByUserId(@PathVariable("userId") long userId){
        List<ReservationDto> reservationDtos = reservationService.getReservationByUserId(userId);
        return ResponseEntity.ok(reservationDtos);
    }

    @PostMapping("/reservation/create")
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto reservationDto) {
        System.out.println("Gelen reservationDto: " + reservationDto);
        try {
            ReservationDto savedReservation = reservationService.createReservation(reservationDto);
            return new ResponseEntity<>(savedReservation, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


}
