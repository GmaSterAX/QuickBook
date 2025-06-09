package com.softwarearchitecture.QuickBook.Mapper;

import com.softwarearchitecture.QuickBook.Dto.PaymentDto;
import com.softwarearchitecture.QuickBook.Model.Payment;
import com.softwarearchitecture.QuickBook.Model.Reservation;

public class PaymentMapper {

    public static PaymentDto mapToPaymentDto(Payment payment) {
       return new PaymentDto(
        payment.getId(),
        payment.getPayment_situation(),
        payment.getReservation().getId(),
        payment.getReservation().getPrice()
       );
    }

    public static Payment mapToPayment(PaymentDto paymentDto, Reservation reservation ){
        return new Payment(
                paymentDto.getId(),
                paymentDto.getPayment_situation(),
                paymentDto.getReservation_price(),
                reservation
        );
    }
}

