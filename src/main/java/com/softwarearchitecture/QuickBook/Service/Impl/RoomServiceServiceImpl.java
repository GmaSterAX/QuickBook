package com.softwarearchitecture.QuickBook.Service.Impl;

import com.softwarearchitecture.QuickBook.Dto.RoomServiceDto;
import com.softwarearchitecture.QuickBook.Exception.ResourceNotFoundException;
import com.softwarearchitecture.QuickBook.Mapper.RoomServiceMapper;
import com.softwarearchitecture.QuickBook.Model.RoomService;
import com.softwarearchitecture.QuickBook.Repository.RoomServiceRepository;
import com.softwarearchitecture.QuickBook.Service.RoomServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceServiceImpl implements RoomServiceService {
    private RoomServiceRepository roomServiceRepository;

    @Autowired
    public RoomServiceServiceImpl(RoomServiceRepository roomServiceRepository){
        this.roomServiceRepository = roomServiceRepository;
    }
    @Override
    public RoomServiceDto getRoomServiceById(long roomServiceId) {
        RoomService roomService = roomServiceRepository.findById(roomServiceId);
        return RoomServiceMapper.mapToRoomServiceDto(roomService);
    }

    @Override
    public List<RoomServiceDto> getRoomServiceByRoomId(long roomId) {
        List<RoomService> roomServicesList = roomServiceRepository.findByRoom_RoomId(roomId);
        return roomServicesList.stream()
                .map(RoomServiceMapper::mapToRoomServiceDto)
                .collect(Collectors.toList());
    }

}
