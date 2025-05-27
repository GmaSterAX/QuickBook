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
                reservation.isCheck_in(),
                reservation.isCheck_out(),
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
        Reservation reservation = new Reservation(
                dto.getId(),
                dto.isCheck_in(),
                dto.isCheck_out(),
                dto.getPrice()
        );
        reservation.setUser(user);
        reservation.setHotel(hotel);
        reservation.setRoom(room);
        room.setReservation(reservation);

        return reservation;
    }

}