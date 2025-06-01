package com.softwarearchitecture.QuickBook.Service;

import com.softwarearchitecture.QuickBook.Dto.HotelServiceDto;

import java.util.List;

public interface HotelServiceService {

    List<HotelServiceDto> getByServiceName(String serviceName);
    List <HotelServiceDto> getAllByHotelId(long hotelId);

}

