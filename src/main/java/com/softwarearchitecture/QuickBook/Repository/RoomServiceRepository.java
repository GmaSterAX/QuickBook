package com.softwarearchitecture.QuickBook.Repository;

import com.softwarearchitecture.QuickBook.Model.RoomService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomServiceRepository extends JpaRepository<RoomService, Long> {
    RoomService findById(long roomServiceId);
    List<RoomService> findByRoom_RoomId(long roomId);
    List<RoomService> findByRoom_RoomIdAndRoomType(long roomId, String roomType);



}
