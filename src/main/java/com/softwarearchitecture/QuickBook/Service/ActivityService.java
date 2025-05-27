package com.softwarearchitecture.QuickBook.Service;

import com.softwarearchitecture.QuickBook.Dto.ActivityDto;

import java.util.List;
import java.util.Optional;

public interface ActivityService {
    List<ActivityDto> getAllByHotelId(long hotelId);

}
