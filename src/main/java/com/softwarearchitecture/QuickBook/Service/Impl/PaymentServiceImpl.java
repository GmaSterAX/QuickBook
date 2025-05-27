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

import java.util.List;
import java.util.stream.Collectors;

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
                .state("UNREAD")
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
    public List<PaymentDto> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream()
                .map(PaymentMapper::mapToPaymentDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDto updatePayment(Long id, PaymentDto paymentDto) {
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));

        Reservation reservation = reservationRepository.findById(paymentDto.getReservation_id());
        if (reservation == null) {
            throw new RuntimeException("Reservation not found with id: " + paymentDto.getReservation_id());
        }

        existingPayment.setReservation_price(paymentDto.getReservation_price());
        existingPayment.setPayment_method(paymentDto.getPayment_method());
        existingPayment.setPayment_situation(paymentDto.isPayment_situation());
        existingPayment.setReservation(reservation);

        Payment updatedPayment = paymentRepository.save(existingPayment);
        return PaymentMapper.mapToPaymentDto(updatedPayment);
    }

    @Override
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        paymentRepository.delete(payment);
    }
}
