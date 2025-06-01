package com.softwarearchitecture.QuickBook.Repository;

import com.softwarearchitecture.QuickBook.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation findById(long reservationId);
    List<Reservation> findByUserId(long userId);
    List<Reservation> findByRoom_RoomId(long roomId);
}
