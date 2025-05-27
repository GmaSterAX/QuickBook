package com.softwarearchitecture.QuickBook.Service;


import com.softwarearchitecture.QuickBook.Dto.PaymentDto;

import java.util.List;

public interface PaymentService {
    PaymentDto createPayment(PaymentDto paymentDto);

    PaymentDto getPaymentById(Long id);

    PaymentDto getPaymentByReservationId(Long reservationId);

    List<PaymentDto> getAllPayments();

    PaymentDto updatePayment(Long id, PaymentDto paymentDto);

    void deletePayment(Long id);
}
