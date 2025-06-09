package com.softwarearchitecture.QuickBook.Service;

import com.softwarearchitecture.QuickBook.Dto.PaymentDto;

public interface PaymentService {
    PaymentDto createPayment(PaymentDto paymentDto);
    PaymentDto getPaymentById(Long id);
    PaymentDto getPaymentByReservationId(Long reservationId);
    PaymentDto updatePayment(Long id, String payment_situation);
}