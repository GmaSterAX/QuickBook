package com.softwarearchitecture.QuickBook.Controller;

import com.softwarearchitecture.QuickBook.Dto.PaymentDto;
import com.softwarearchitecture.QuickBook.Dto.ReservationDto;
import com.softwarearchitecture.QuickBook.Dto.UserDto;
import com.softwarearchitecture.QuickBook.Service.PaymentService;
import com.softwarearchitecture.QuickBook.Service.ReservationService;
import com.softwarearchitecture.QuickBook.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;    
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;
    private final ReservationService reservationService;

    @Autowired
    public PaymentController(PaymentService paymentService, 
                             UserService userService,
                             ReservationService reservationService){
        this.paymentService = paymentService;
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @PostMapping("/add")
    public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentDto paymentDto){
        PaymentDto created = paymentService.createPayment(paymentDto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/update-payment/{id}")
    public ResponseEntity<PaymentDto> updatePayment(
        @PathVariable Long id, 
        @RequestParam String status) {
        
        PaymentDto updated = paymentService.updatePayment(id, status);
        return ResponseEntity.ok(updated);
    }
    
    @GetMapping("/my-payments")
    public String getUserPayments(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userMail = authentication.getName();
        UserDto user = userService.getUserByMail(userMail);
        List<ReservationDto> reservationDtos = reservationService.getReservationByUserId(user.getId());
        List<PaymentDto> payments = new ArrayList<>();
        for (ReservationDto reservation : reservationDtos) {
            payments.add(paymentService.getPaymentByReservationId(reservation.getId()));
        }
        model.addAttribute("payments", payments);
        return "my-payments";
    }
}