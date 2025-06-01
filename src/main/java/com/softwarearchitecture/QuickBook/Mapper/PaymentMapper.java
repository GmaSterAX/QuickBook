package com.softwarearchitecture.QuickBook.Mapper;

import com.softwarearchitecture.QuickBook.Dto.PaymentDto;
import com.softwarearchitecture.QuickBook.Model.Payment;
import com.softwarearchitecture.QuickBook.Model.Reservation;
import org.springframework.security.core.parameters.P;

public class PaymentMapper {

    public static PaymentDto mapToPaymentDto(Payment payment) {
       return new PaymentDto(
        payment.getId(),
        payment.getReservation_price(),
        payment.getPayment_method(),
        payment.isPayment_situation(),
        payment.getReservation().getId()
       );
    }

    public static Payment mapToPayment(PaymentDto paymentDto) {
        return new Payment(
                paymentDto.getId(),
                paymentDto.getReservation_price(),
                paymentDto.getPayment_method(),
                paymentDto.isPayment_situation()

        );
    }
}

