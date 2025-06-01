package com.softwarearchitecture.QuickBook.Mapper;

import com.softwarearchitecture.QuickBook.Dto.HotelServiceDto;
import com.softwarearchitecture.QuickBook.Model.HotelService;

public class HotelServiceMapper {

    public static HotelServiceDto mapToHotelServiceDto(HotelService hotelService) {
        return new HotelServiceDto(
                hotelService.getId(),
                hotelService.getServiceName(),
                hotelService.getHotel().getId()
        );
    }

    public static HotelService mapToHotelService(HotelServiceDto hotelServiceDto) {
        return new HotelService(
                hotelServiceDto.getId(),
                hotelServiceDto.getService_name()

        );

    }
}
