package com.softwarearchitecture.QuickBook.Mapper;

import com.softwarearchitecture.QuickBook.Dto.RoomServiceDto;
import com.softwarearchitecture.QuickBook.Model.RoomService;

public class RoomServiceMapper {

    public static RoomServiceDto mapToRoomServiceDto(RoomService roomService){
        return new RoomServiceDto(
                roomService.getId(),
                roomService.getService_name(),
                roomService.getRoomType(),
                roomService.getRoom().getRoomId()
        );
    }

    public static RoomService mapToRoomService(RoomServiceDto roomServiceDto){
        return new RoomService(
                roomServiceDto.getId(),
                roomServiceDto.getServiceName(),
                roomServiceDto.getRoomType()
        );
    }
}
