package com.softwarearchitecture.QuickBook.Service;

import com.softwarearchitecture.QuickBook.Dto.RoomServiceDto;

import java.util.List;


public interface RoomServiceService {
    RoomServiceDto getRoomServiceById(long id);
    List<RoomServiceDto> getRoomServiceByRoomId(long roomId);
    List<RoomServiceDto> getRoomServiceByRoomIdAndRoomType(long roomId, String roomType);
}
