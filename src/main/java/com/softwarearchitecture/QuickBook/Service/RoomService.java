package com.softwarearchitecture.QuickBook.Service;

import com.softwarearchitecture.QuickBook.Dto.RoomDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {

    List<RoomDto> getByRoomCapacity(int capacity);
}
