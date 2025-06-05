package com.softwarearchitecture.QuickBook.Service;

import com.softwarearchitecture.QuickBook.Dto.ReservationDto;

import java.util.List;

public interface ReservationService {
    ReservationDto getReservationById(long reservationId);
    List<ReservationDto> getReservationByUserId(long userId);
    ReservationDto saveReservation(ReservationDto reservationDto);

}