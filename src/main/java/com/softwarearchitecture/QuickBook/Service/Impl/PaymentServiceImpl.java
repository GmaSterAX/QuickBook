package com.softwarearchitecture.QuickBook.Service.Impl;

import com.softwarearchitecture.QuickBook.Dto.NotificationDto;
import com.softwarearchitecture.QuickBook.Dto.PaymentDto;
import com.softwarearchitecture.QuickBook.Mapper.PaymentMapper;
import com.softwarearchitecture.QuickBook.Model.Payment;
import com.softwarearchitecture.QuickBook.Model.Reservation;
import com.softwarearchitecture.QuickBook.Repository.PaymentRepository;
import com.softwarearchitecture.QuickBook.Repository.ReservationRepository;
import com.softwarearchitecture.QuickBook.Service.NotificationService;
import com.softwarearchitecture.QuickBook.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final NotificationService notificationService;


    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              ReservationRepository reservationRepository,
                              NotificationService notificationService) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
        this.notificationService = notificationService;
    }

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto) {
        Reservation reservation = reservationRepository.findById(paymentDto.getReservation_id());
        if (reservation == null) {
            throw new RuntimeException("Reservation not found with id: " + paymentDto.getReservation_id());
        }

        Payment existingPayment = paymentRepository.findByReservationId(reservation.getId());
        if (existingPayment != null) {
            throw new RuntimeException("Payment already exists for this reservation");
        }

        Payment payment = PaymentMapper.mapToPayment(paymentDto, reservation);
        Payment savedPayment = paymentRepository.save(payment);

        // payment olunca bildirim gönderiyoruz
        NotificationDto notificationDto = NotificationDto.builder()
                .message("Your reservation has been confirmed. Have a great holiday!")
                .messageTitle("Payment")
                .user_id(reservation.getUser().getId())
                .build();

        notificationService.createNotification(notificationDto);
        return PaymentMapper.mapToPaymentDto(savedPayment);
    }

    @Override
    public PaymentDto getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        return PaymentMapper.mapToPaymentDto(payment);
    }

    @Override
    public PaymentDto getPaymentByReservationId(Long reservationId) {
        Payment payment = paymentRepository.findByReservationId(reservationId);
        if (payment == null) {
            throw new RuntimeException("Payment not found for reservation id: " + reservationId);
        }
        return PaymentMapper.mapToPaymentDto(payment);
    }

    @Override
    @Transactional
    public PaymentDto updatePayment(Long id, String payment_situation) {
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));

        existingPayment.setPayment_situation(payment_situation);
        
        Payment updatedPayment = paymentRepository.save(existingPayment);
        return PaymentMapper.mapToPaymentDto(updatedPayment);
    }
}
