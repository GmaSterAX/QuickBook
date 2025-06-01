package com.softwarearchitecture.QuickBook.Mapper;

import com.softwarearchitecture.QuickBook.Dto.ReservationDto;
import com.softwarearchitecture.QuickBook.Model.Hotel;
import com.softwarearchitecture.QuickBook.Model.Reservation;
import com.softwarearchitecture.QuickBook.Model.Room;
import com.softwarearchitecture.QuickBook.Model.User;

public class ReservationMapper {

    public static ReservationDto mapToReservationDto(Reservation reservation) {
        return new ReservationDto(
                reservation.getId(),
                reservation.getStart_date(),
                reservation.getEnd_date(),
                reservation.getPrice(),
                reservation.getUser().getId(),
                reservation.getHotel().getId(),
                reservation.getRoom().getRoomId()
        );
    }

    public static Reservation mapToReservation(
            ReservationDto dto,
            User user,
            Hotel hotel,
            Room room
    ) {
        Reservation reservation = Reservation.builder()
                .id(dto.getId())
                .start_date(dto.getStart_date())
                .end_date(dto.getEnd_date())
                .price(dto.getPrice())
                .user(user)
                .hotel(hotel)
                .room(room)
                .build();

        // Tek tek atmak yerine listeye ekle
        room.getReservation().add(reservation);

        return reservation;
    }

}
