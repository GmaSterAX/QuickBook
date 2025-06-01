package com.softwarearchitecture.QuickBook.Service;

import com.softwarearchitecture.QuickBook.Dto.RoomDto;

import java.util.List;

public interface RoomService {
    List<RoomDto> getRoomByCapacity(int capacity);
    List<RoomDto> getRoomByHotel_Id(long hotelId);
    List<RoomDto> getRoomsByCityAndCapacity(String city, int capacity);
    List<RoomDto> getRoomsByCityHotelAndCapacity(String city, long hotelId, int capacity);
    RoomDto getRoomById(long roomId);
    
}
