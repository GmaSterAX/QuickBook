package com.softwarearchitecture.QuickBook.Service;

import com.softwarearchitecture.QuickBook.Dto.HotelDto;

import java.util.List;

public interface HotelService {
    HotelDto getHotelById(long hotelId);
    List<HotelDto> getAllHotels();
    List<HotelDto> getHotelByCity(String city);
    List<HotelDto> getHotelByPoint(double point) throws IllegalArgumentException;

}
