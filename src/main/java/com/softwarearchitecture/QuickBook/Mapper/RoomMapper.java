package com.softwarearchitecture.QuickBook.Mapper;

import com.softwarearchitecture.QuickBook.Dto.RoomDto;
import com.softwarearchitecture.QuickBook.Model.Room;

public class RoomMapper {

    public static RoomDto mapToRoomDto(Room room){
        return new RoomDto(
                room.getRoomId(),
                room.getRoomNumber(),
                room.getPrice(),
                room.isReserved(),
                room.getCapacity(),
                room.getHotel().getId()
        );
    }

    public static Room mapToRoom(RoomDto roomDto){
        return new Room(
                roomDto.getRoom_id(),
                roomDto.getRoomNumber(),
                roomDto.getPrice(),
                roomDto.isReserved(),
                roomDto.getCapacity()
        );
    }
}