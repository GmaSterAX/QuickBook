package com.softwarearchitecture.QuickBook.Service.Impl;

import com.softwarearchitecture.QuickBook.Dto.RoomDto;
import com.softwarearchitecture.QuickBook.Mapper.RoomMapper;
import com.softwarearchitecture.QuickBook.Model.Room;
import com.softwarearchitecture.QuickBook.Repository.RoomRepository;
import com.softwarearchitecture.QuickBook.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomDto> getRoomByCapacity(int capacity) {
        List<Room> rooms = roomRepository.findByCapacity(capacity);
        return rooms.stream()
                .map(RoomMapper::mapToRoomDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomDto> getRoomsByCityAndCapacity(String city, int capacity) {
        List<Room> rooms = roomRepository.findByHotel_CityIgnoreCaseAndCapacity(city, capacity);
        return rooms.stream()
                .map(RoomMapper::mapToRoomDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomDto> getRoomsByCityHotelAndCapacity(String city, long hotelId, int capacity) {
        List<Room> rooms = roomRepository.findByHotel_CityIgnoreCaseAndHotel_IdAndCapacity(city, hotelId, capacity);
        return rooms.stream()
                .map(RoomMapper::mapToRoomDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomDto> getRoomByHotel_Id(long hotelId) {
        List<Room> rooms = roomRepository.findByHotel_Id(hotelId);
        return rooms.stream()
        .map(RoomMapper::mapToRoomDto)
        .collect(Collectors.toList());
    }


}
