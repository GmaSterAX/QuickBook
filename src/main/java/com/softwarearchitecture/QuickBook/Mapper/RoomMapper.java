package com.softwarearchitecture.QuickBook.Mapper;

import com.softwarearchitecture.QuickBook.Dto.RoomDto;
import com.softwarearchitecture.QuickBook.Model.Room;

public class RoomMapper {

    public static RoomDto mapToRoomDto(Room room){
       return new RoomDto(
               room.getId(),
               room.getHotel_id(),
               room.getPrice(),
               room.isReserved(),
               room.getCapacity()
       );
    }

    public static Room mapToRoom(RoomDto roomDto){
        return new Room(
                roomDto.getId(),
                roomDto.getHotel_id(),
                roomDto.getPrice(),
                roomDto.isReserved(),
                roomDto.getCapacity()

        );
    }
}
