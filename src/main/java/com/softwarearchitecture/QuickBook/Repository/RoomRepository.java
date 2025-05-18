package com.softwarearchitecture.QuickBook.Repository;

import com.softwarearchitecture.QuickBook.Model.Room;
import com.softwarearchitecture.QuickBook.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room,Long> {
    Optional<Room> findById(Long id);
    List<Room> findByCapacity (int capacity);
}
