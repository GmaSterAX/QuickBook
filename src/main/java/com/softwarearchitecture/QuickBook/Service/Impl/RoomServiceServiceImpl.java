package com.softwarearchitecture.QuickBook.Service.Impl;

import com.softwarearchitecture.QuickBook.Dto.NotificationDto;
import com.softwarearchitecture.QuickBook.Dto.RoomServiceDto;
import com.softwarearchitecture.QuickBook.Mapper.RoomServiceMapper;
import com.softwarearchitecture.QuickBook.Model.RoomService;
import com.softwarearchitecture.QuickBook.Repository.RoomServiceRepository;
import com.softwarearchitecture.QuickBook.Service.NotificationService;
import com.softwarearchitecture.QuickBook.Service.RoomServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceServiceImpl implements RoomServiceService {
    private RoomServiceRepository roomServiceRepository;
    private final NotificationService notificationService;


    @Autowired
    public RoomServiceServiceImpl(RoomServiceRepository roomServiceRepository,
                                  NotificationService notificationService){
        this.roomServiceRepository = roomServiceRepository;
        this.notificationService = notificationService;
    }

    @Override
    public RoomServiceDto getRoomServiceById(long roomServiceId) {
        RoomService roomService = roomServiceRepository.findById(roomServiceId);

        // Artık doğrudan reservation'a erişebilirsiniz
        NotificationDto notificationDto = NotificationDto.builder()
                .message("You have received the service: " + roomService.getService_name() + ". Enjoy!")
                .messageTitle("Room Services")
                .user_id(roomService.getReservation().getUser().getId()) // Artık çalışacak
                .build();
        notificationService.createNotification(notificationDto);

        return RoomServiceMapper.mapToRoomServiceDto(roomService);
    }

    @Override
    public List<RoomServiceDto> getRoomServiceByRoomId(long roomId) {
        List<RoomService> roomServicesList = roomServiceRepository.findByRoom_RoomId(roomId);
        return roomServicesList.stream()
                .map(RoomServiceMapper::mapToRoomServiceDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<RoomServiceDto> getRoomServiceByRoomIdAndRoomType(long roomId, String roomType) {
        List<RoomService> roomServices = roomServiceRepository.findByRoom_RoomIdAndRoomType(roomId, roomType);
        return roomServices.stream()
                .map(RoomServiceMapper::mapToRoomServiceDto)
                .collect(Collectors.toList());
    }



}
