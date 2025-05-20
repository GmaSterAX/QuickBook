package com.softwarearchitecture.QuickBook.Mapper;

import com.softwarearchitecture.QuickBook.Dto.HotelDto;
import com.softwarearchitecture.QuickBook.Model.Hotel;

public class HotelMapper {

    public static HotelDto mapToHotelDto(Hotel hotel){
        return new HotelDto(
                hotel.getId(),
                hotel.getName(),
                hotel.getAddress(),
                hotel.getCity(),
                hotel.getPhone(),
                hotel.getPoint(),
                hotel.getImg_url()
        );
    }

    public static Hotel mapToHotel(HotelDto hotelDto){
        return new Hotel(
                hotelDto.getId(),
                hotelDto.getName(),
                hotelDto.getAddress(),
                hotelDto.getCity(),
                hotelDto.getPhone(),
                hotelDto.getPoint(),
                hotelDto.getImg_url()
        );
    }
}
