package com.softwarearchitecture.QuickBook.Service.Impl;

import com.softwarearchitecture.QuickBook.Dto.RoomDto;
import com.softwarearchitecture.QuickBook.Exception.ResourceNotFoundException;
import com.softwarearchitecture.QuickBook.Mapper.RoomMapper;
import com.softwarearchitecture.QuickBook.Model.Room;
import com.softwarearchitecture.QuickBook.Repository.RoomRepository;
import com.softwarearchitecture.QuickBook.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {this.roomRepository = roomRepository;}

    @Override
    public List<RoomDto> getByRoomCapacity(int capacity) {
        List<Room> rooms = roomRepository.findByCapacity(capacity);
                return rooms.stream()
                        .map(RoomMapper:: mapToRoomDto)
                        .collect(Collectors.toList());
    }
}
