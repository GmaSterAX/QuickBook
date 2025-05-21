package com.softwarearchitecture.QuickBook.Repository;

import com.softwarearchitecture.QuickBook.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findById(long roomId);
    List<Room> findByCapacity(int capacity);
    List<Room> findByHotel_CityIgnoreCaseAndCapacity(String city, int capacity);
    List<Room> findByHotel_CityIgnoreCaseAndHotel_IdAndCapacity(String city, long hotelId, int capacity);
}
