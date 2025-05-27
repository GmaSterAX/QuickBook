package com.softwarearchitecture.QuickBook.Mapper;

import com.softwarearchitecture.QuickBook.Dto.PaymentDto;
import com.softwarearchitecture.QuickBook.Model.Payment;
import com.softwarearchitecture.QuickBook.Model.Reservation;

public class PaymentMapper {

    public static PaymentDto mapToPaymentDto(Payment payment) {
        PaymentDto dto = new PaymentDto();
        dto.setId(payment.getId());
        dto.setReservation_price(payment.getReservation_price());
        dto.setPayment_method(payment.getPayment_method());
        dto.setPayment_situation(payment.isPayment_situation());
        dto.setReservation_id(payment.getReservation().getId());
        return dto;
    }

    public static Payment mapToPayment(PaymentDto dto, Reservation reservation) {
        Payment payment = new Payment();
        payment.setId(dto.getId());
        payment.setReservation_price(dto.getReservation_price());
        payment.setPayment_method(dto.getPayment_method());
        payment.setPayment_situation(dto.isPayment_situation());
        payment.setReservation(reservation);
        return payment;
    }
}

